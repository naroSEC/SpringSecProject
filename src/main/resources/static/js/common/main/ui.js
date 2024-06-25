$(function() {
    const sidebar = $('.sidebar');
    const sidebar_toggle = $('.sidebar-toggle');
    const sidebar_close = $('.sidebar-close');
    const drop_act = $('#drop-act');

    sidebar_toggle.click(function(e) {
        sidebar.css('left', '0');
        sidebar_toggle.css('top', '-100px');
    });

    sidebar_close.click(function(e) {
        sidebar.css('left', '-300px');
        sidebar_toggle.css('top', '20px');
    });

    drop_act.click(function(e) {
        if (drop_act.hasClass('dropup')) {
            drop_act.removeClass('dropup');
            drop_act.addClass('dropdown');
        } else {
            drop_act.removeClass('dropdown');
            drop_act.addClass('dropup');
        }
    });
});