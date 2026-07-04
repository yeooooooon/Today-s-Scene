// ==========================================
// 오늘의 씬 - 영화 데이터 + TMDB API 연동
// ==========================================
const TMDB_API_KEY = '07095f4f93d3be485e207578d4a61624';
const TMDB_BASE = 'https://api.themoviedb.org/3';
const TMDB_IMG = 'https://image.tmdb.org/t/p/w500';
const TMDB_IMG_LG = 'https://image.tmdb.org/t/p/original';

// ==========================================
// 무드별 영화 풀 (각 15편) + 추천 이유 풀
// 같은 무드를 골라도 매번 랜덤으로 3편씩!
// ==========================================
const MOOD_DATA = {
  melancholy: {
    moviePool: [
      // 명작 클래식
      152601, 153, 38, 334541, 376867, 84892, 62215, 142, 428449, 2841, 489, 1124,
      // 최신 인기작 (2023~2026)
      666277, 976893, 906126, 508883, 965150, 466272
    ],
    reasonPool: [
      '혼자 있는 밤, 이 영화가 조용히 곁에 있어줄 거예요',
      '잔잔하지만 오래 마음에 남는 여운이 있는 영화예요',
      '슬프지만 동시에 아름다운, 묘한 감동의 영화예요',
      '비 오는 밤 따뜻한 음료 한 잔이랑 보면 완벽해요',
      '엔딩 크레딧 올라가도 한참 자리에서 못 일어날 거예요',
      '혼자 있는 시간을 더 깊게 만들어주는 영화예요',
      '먹먹한 감정이 오래 가슴에 남는 영화예요',
      '지치고 쓸쓸한 밤, 조용히 위로해주는 영화예요',
    ],
  },
  excited: {
    moviePool: [
      // 명작 클래식
      27205, 155, 76341, 299536, 245891, 603, 280, 1571, 575264, 1726, 118340,
      // 최신 인기작 (2023~2026)
      533535, 569094, 746036, 718821, 911430, 603692, 786892, 653346, 1242898
    ],
    reasonPool: [
      '무조건 몰입 보장. 중간에 자리를 떠날 수 없어요',
      '사운드 좋은 환경에서 보면 완전히 다른 경험이에요',
      '짜릿함을 넘어 충격까지 — 강렬한 밤 보장',
      '아드레날린이 폭발하는 액션 시퀀스가 압권이에요',
      '큰 화면에서 봐야 진짜 맛이 나는 영화예요',
      '심장이 뛰는 걸 느끼고 싶은 밤에 딱이에요',
      '시작부터 끝까지 한순간도 지루할 틈이 없어요',
      '에너지가 필요한 날 밤, 보고 나면 기분 전환 완료',
    ],
  },
  romantic: {
    moviePool: [
      // 명작 클래식 (어바웃 타임, 노트북, 라라랜드 등)
      122906, 11036, 313369, 597, 76, 508, 19913, 4348, 50646, 13,
      // 최신 인기작 (2023~2026)
      937287, 1072790, 976573, 1000075, 666277, 950396, 1218925
    ],
    reasonPool: [
      '설레는 감정 그 자체를 2시간 동안 느낄 수 있어요',
      '연애 경험이 있는 사람이라면 누구나 공감해요',
      '보고 나서 한동안 OST가 머릿속을 맴돌 거예요',
      '두근거리는 마음이 그리워질 때 딱이에요',
      '첫사랑이 떠오르는, 감성 충만한 영화예요',
      '소중한 사람과 함께 봐도 좋은 따뜻한 영화예요',
      '마음이 몽글몽글해지는 영화예요',
      '사랑이라는 감정의 모든 색깔을 보여주는 영화예요',
    ],
  },
  dark: {
    moviePool: [
      // 명작 클래식
      807, 274, 210577, 419430, 496243, 11324, 146233, 1593, 242582, 694, 539,
      // 최신 인기작 (2023~2026)
      1226578, 945961, 929590, 1242898, 1038392, 762441, 1111873, 1064028, 1173559
    ],
    reasonPool: [
      '긴장감이 극에 달하는 밤, 이 영화라면 충분해요',
      '보고 나서 오래 생각하게 되는, 여운이 짙은 영화예요',
      '몰입도 극강의 심리 스릴러, 집중하고 보세요',
      '소름이 돋는 분위기를 즐기고 싶은 밤에요',
      '어두운 인간 본성을 들여다보는 영화예요',
      '결말까지 숨이 차오르는 긴장감의 연속이에요',
      '한 번 보면 잊히지 않는 강렬한 영화예요',
      '예상치 못한 반전이 기다리고 있어요',
    ],
  },
  cozy: {
    moviePool: [
      // 명작 클래식
      129, 8392, 257211, 773, 324852, 194, 24803, 16859, 2062, 13, 120467,
      // 최신 인기작 (2023~2026)
      1022789, 840430, 1022796, 1084242, 1327819, 976893, 1002266, 1226863, 939243
    ],
    reasonPool: [
      '담요 덮고 따뜻한 음료 한 잔과 함께 보면 완벽해요',
      '보고 나면 지금 이 순간이 소중하게 느껴져요',
      '지치고 힘든 날, 꼭 안아주는 것 같은 영화예요',
      '마음이 따뜻해지는 잔잔한 위로가 되는 영화예요',
      '온 가족이 같이 봐도 좋은 따뜻한 영화예요',
      '하루의 피로가 풀리는, 마음을 어루만져주는 영화예요',
      '슬며시 미소짓게 만드는, 그런 영화예요',
      '추운 계절에 이불 속에서 보면 완벽해요',
    ],
  },
  wonder: {
    moviePool: [
      // 명작 클래식
      157336, 19995, 671, 120, 49047, 329865, 62, 286217, 24428, 13475, 119450,
      // 최신 인기작 (2023~2026)
      693134, 872585, 1045631, 83533, 687163, 653346, 539972, 786892, 823464
    ],
    reasonPool: [
      '사운드 좋은 환경에서 보면 경험이 완전히 달라져요',
      '끝나고 한참 동안 아무 말도 하고 싶지 않아질 영화예요',
      '영화라기보다 하나의 우주적 경험에 가까워요',
      '큰 화면, 좋은 사운드 시스템에서 봐야 제맛이에요',
      '인간이 얼마나 작은 존재인지 느끼게 되는 영화예요',
      '세상의 광활함을 몸으로 느끼는 영화예요',
      '경이로움이라는 단어의 정확한 의미를 알게 돼요',
      '영화관에서 봤어야 했다는 후회가 남을 작품이에요',
    ],
  },
  funny: {
    moviePool: [
      // 명작 클래식
      18785, 8363, 293660, 64688, 12133, 14160, 9806, 105, 9536, 585, 2062,
      // 최신 인기작 (2023~2026)
      346698, 1022787, 836018, 533535, 1084242, 746036, 1198994, 1327819, 1266127
    ],
    reasonPool: [
      '웃고 싶은데 내용도 있었으면 하는 밤에 딱이에요',
      '울다가 웃다가, 기분이 정화되는 느낌이에요',
      '엔딩에서 같이 노래 부르고 싶어질 거예요',
      '실컷 웃고 나면 스트레스가 싹 풀려요',
      '가볍게 즐기고 싶은 날 밤에 완벽해요',
      '웃음 코드가 통통 튀는, 부담 없는 영화예요',
      '친구랑 같이 보면 더 재밌어요',
      '복잡한 생각 다 내려놓고 그냥 즐기세요',
    ],
  },
  thoughtful: {
    moviePool: [
      // 명작 클래식
      496243, 329865, 37165, 603, 550, 37799, 152601, 13, 11324, 77, 398818, 1422,
      // 최신 인기작 (2023~2026)
      872585, 792307, 467244, 929590, 936075, 906126, 666277, 687163, 1330021
    ],
    reasonPool: [
      '보고 나서 혼자서 한참 생각하게 되는 영화예요',
      '보고 나면 SNS 알림을 잠깐 꺼두고 싶어져요',
      '보고 나면 인간 관계에 대해 한참 생각하게 돼요',
      '엔딩 크레딧 올라갈 때 멍하니 앉아있게 돼요',
      '한 번 더 봐야 진짜 의미가 보이는 영화예요',
      '인생에 대해 다시 생각하게 만드는 영화예요',
      '보고 나면 누군가와 이야기 나누고 싶어져요',
      '오래 기억에 남는, 묵직한 영화예요',
    ],
  },
};

// ==========================================
// 랜덤으로 N개 뽑기
// ==========================================
function pickRandom(arr, n) {
  const shuffled = [...arr].sort(() => Math.random() - 0.5);
  return shuffled.slice(0, n);
}

// 오늘의 추천 영화 3편 + 추천 이유 3개 가져오기 (백엔드 연동 완료)
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
