// ==========================================
// 오늘의 씬 - 영화 데이터 + TMDB API 연동
// ==========================================
const TMDB_API_KEY = '07095f4f93d3be485e207578d4a61624';
const TMDB_BASE = 'https://api.themoviedb.org/3';
const TMDB_IMG = 'https://image.tmdb.org/t/p/w500';
const TMDB_IMG_LG = 'https://image.tmdb.org/t/p/original';

// ==========================================
// 랜덤으로 N개 뽑기
// ==========================================
function pickRandom(arr, n) {
  const shuffled = [...arr].sort(() => Math.random() - 0.5);
  return shuffled.slice(0, n);
}

async function getTodaysPick(mood) {
  try {
    const res = await fetch(`http://localhost:8080/api/moods/${mood}`);
    if (!res.ok) throw new Error('백엔드에서 데이터를 가져오지 못했습니다.');
    const data = await res.json();
    
    const moviePool = data.movies.map(m => m.movieId);
    const reasonPool = data.reasons.map(r => r.reasonText);

    return {
      movies: pickRandom(moviePool, 3),
      reasons: pickRandom(reasonPool, 3),
    };
  } catch (err) {
    console.error("백엔드 연동 에러:", err);
    return null;
  }
}

// ==========================================
// TMDB API 호출 함수
// ==========================================

async function fetchMovie(movieId) {
  const url = `${TMDB_BASE}/movie/${movieId}?api_key=${TMDB_API_KEY}&language=ko-KR`;
  const res = await fetch(url);
  if (!res.ok) throw new Error('영화 정보를 가져오지 못했어요');
  return await res.json();
}

async function fetchMovieWithCredits(movieId) {
  const url = `${TMDB_BASE}/movie/${movieId}?api_key=${TMDB_API_KEY}&language=ko-KR&append_to_response=credits`;
  const res = await fetch(url);
  if (!res.ok) throw new Error('영화 정보를 가져오지 못했어요');
  return await res.json();
}

function posterUrl(path, large = false) {
  if (!path) return null;
  return (large ? TMDB_IMG_LG : TMDB_IMG) + path;
}

function getDirector(credits) {
  if (!credits || !credits.crew) return '';
  const dir = credits.crew.find(c => c.job === 'Director');
  return dir ? dir.name : '';
}

function getMainCast(credits) {
  if (!credits || !credits.cast) return '';
  return credits.cast.slice(0, 3).map(c => c.name).join(', ');
}

function getGenres(genres) {
  if (!genres) return '';
  return genres.slice(0, 2).map(g => g.name).join(' · ');
}

function formatRuntime(minutes) {
  if (!minutes) return '—';
  return `${minutes}분`;
}

// ==========================================
// 영화 검색 (TMDB /search/movie)
// ==========================================
async function searchMovies(query) {
  if (!query || !query.trim()) return [];
  const url = `${TMDB_BASE}/search/movie?api_key=${TMDB_API_KEY}&language=ko-KR&query=${encodeURIComponent(query)}&include_adult=false`;
  const res = await fetch(url);
  if (!res.ok) throw new Error('검색에 실패했어요');
  const data = await res.json();
  return data.results || [];
}

// ==========================================
// 영화 트레일러 후보 리스트 가져오기
// 연령제한/임베드 차단된 영상이 있을 수 있으므로
// 여러 후보를 우선순위 정렬해서 반환 → 클라이언트에서 순차 시도
// ==========================================
async function fetchTrailerCandidates(movieId) {
  // 한국어 + 영어 결과를 합쳐서 우선순위로 정렬
  const fetchVideos = async (lang) => {
    const url = `${TMDB_BASE}/movie/${movieId}/videos?api_key=${TMDB_API_KEY}&language=${lang}`;
    const res = await fetch(url);
    if (!res.ok) return [];
    const data = await res.json();
    return data.results || [];
  };

  const [koVideos, enVideos] = await Promise.all([
    fetchVideos('ko-KR'),
    fetchVideos('en-US'),
  ]);

  // 중복 제거 (key 기준)
  const seen = new Set();
  const all = [...koVideos, ...enVideos].filter(v => {
    if (seen.has(v.key)) return false;
    seen.add(v.key);
    return v.site === 'YouTube';
  });

  // 우선순위 점수 계산
  // - 공식(official) trailer가 가장 좋음
  // - Trailer > Teaser > Clip
  // - 최신 영상 우선
  const score = (v) => {
    let s = 0;
    if (v.type === 'Trailer') s += 100;
    else if (v.type === 'Teaser') s += 50;
    else if (v.type === 'Clip') s += 20;
    if (v.official) s += 30;
    return s;
  };

  return all
    .sort((a, b) => score(b) - score(a))
    .map(v => v.key);
}
