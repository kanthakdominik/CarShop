function changeLanguage(lang) {
    let currentUrl = window.location.toString();
    let cleanUrl = currentUrl.split("?")[0];

    window.history.replaceState({}, window.title, cleanUrl + "?lang=" + lang);
    location.reload();
}

$("#select-lang").change(function(){
    changeLanguage($(this).val());
});