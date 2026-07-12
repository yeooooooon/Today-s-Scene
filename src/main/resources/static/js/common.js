// ==========================================
// 공통 기능: 검색 모달
// 모든 페이지에서 import
// ==========================================

// ── 검색 모달 ────────────────────────────
function openSearch() {
  let modal = document.getElementById('searchModal');
  if (!modal) {
    modal = createSearchModal();
    document.body.appendChild(modal);
  }
  modal.classList.add('show');
  setTimeout(() => {
    document.getElementById('searchInput')?.focus();
  }, 100);
}

function closeSearch() {
  const modal = document.getElementById('searchModal');
  if (modal) modal.classList.remove('show');
}

function createSearchModal() {
  const modal = document.createElement('div');
  modal.id = 'searchModal';
  modal.className = 'search-modal';
  modal.innerHTML = `
    <div class="search-overlay" onclick="closeSearch()"></div>
    <div class="search-box">
      <div class="search-input-wrap">
        <span class="search-icon">🔍</span>
        <input type="text" id="searchInput" placeholder="Search for a film..." />
        <button class="search-close" onclick="closeSearch()">✕</button>
      </div>
      <div class="search-results" id="searchResults">
        <p class="search-hint">Type to start searching</p>
      </div>
    </div>
  `;
  return modal;
}

let searchTimer = null;
function handleSearchInput(e) {
  const query = e.target.value.trim();
  clearTimeout(searchTimer);

  if (!query) {
    document.getElementById('searchResults').innerHTML =
      '<p class="search-hint">Type to start searching</p>';
    return;
  }

  searchTimer = setTimeout(async () => {
    const results = document.getElementById('searchResults');
    results.innerHTML = '<p class="search-hint">Searching...</p>';
    try {
      const movies = await searchMovies(query);
      if (movies.length === 0) {
        results.innerHTML = '<p class="search-hint">No results found.</p>';
        return;
      }
      results.innerHTML = movies.slice(0, 10).map(m => `
        <div class="search-item" onclick="selectSearchResult(${m.id})">
          <div class="search-poster" style="background-image:url('${m.poster_path ? posterUrl(m.poster_path) : ''}')"></div>
          <div class="search-info">
            <p class="search-title">${m.title}</p>
            <p class="search-meta">
              ${m.release_date ? m.release_date.slice(0,4) : '—'}
              ${m.vote_average ? ' · ★ ' + m.vote_average.toFixed(1) : ''}
            </p>
            ${m.overview ? `<p class="search-overview">${m.overview.slice(0,80)}${m.overview.length>80?'...':''}</p>` : ''}
          </div>
        </div>
      `).join('');
    } catch (err) {
      results.innerHTML = '<p class="search-hint">Search failed. Check API key.</p>';
    }
  }, 400);
}

function selectSearchResult(movieId) {
  sessionStorage.setItem('movieId', movieId);
  window.location.href = 'detail.html';
}

document.addEventListener('DOMContentLoaded', () => {
  document.addEventListener('input', (e) => {
    if (e.target && e.target.id === 'searchInput') {
      handleSearchInput(e);
    }
  });

  const orb1 = document.createElement('div');
  orb1.className = 'ambient-orb orb-1';
  const orb2 = document.createElement('div');
  orb2.className = 'ambient-orb orb-2';
  document.body.prepend(orb2);
  document.body.prepend(orb1);
});
