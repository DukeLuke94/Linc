
$(document).ready(() => {

    var url = window.location.href;

    if (url.indexOf("#") > 0){
        var activeTab = url.substring(url.indexOf("#") + 1);
        $('.nav[role="tablist"] a[href="#'+activeTab+'"]').tab('show');

    } else {
        $('.nav[role="tablist"] a[href="#v-pills-tasks"]').tab('show');
    }

})
