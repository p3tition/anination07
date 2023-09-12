$(document).ready(function() {
    var loading = false;
    var currentPage = 0;
    const pageSize = 24;

    function loadMoreAnime() {
        if (loading) return;
        loading = true;

        $.get("/load-anime", { page: currentPage, size: pageSize }, function(data) {
            if (data.length > 0) {
                var animeList = $(".anime-list");
                data.forEach(function(anime) {
                    var animeHtml = `
                        <article class="anime_title anime-entry" th:fragment="animeTitleFragment(animeList)">
                            <a href="/anime/${anime.id}">
                                <img src="${anime.photoPath}" alt="title">
                                <p class="title_main_info">${anime.titleEnglish}</p>
                                <p>
                                    studio <span class="title_mini_info">${anime.year}</span>
                                </p>
                            </a>
                            <div class="tooltip">
                                <p class="tooltip_title">${anime.titleEnglish}</p>
                                <p class="tooltip_description">
                                    ${anime.synopsis}
                                </p>
                                <p class="tooltipe_middle">
                                    <span><span class="tooltip_category">type:</span> <span>${anime.type}</span></span>
                                    <span><span class="tooltip_category">episodes:</span> <span >${anime.episodes}</span></span>
                                    <span><span class="tooltip_category">status:</span> <span>${anime.titleStatus}</span></span>
                                    <span><span class="tooltip_category">score:</span> <span>${anime.scoreMal}</span></span>
                                </p>
                                <p>
                                    <span class="tooltip_category">genres:</span> <span>something</span>
                                </p>
                            </div>
                        </article>
                    `;
                    animeList.append(animeHtml);
                });
                currentPage++;
            }
            loading = false;
        });
    }

    loadMoreAnime();

    $(window).scroll(function() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            loadMoreAnime();
        }
    });
});
