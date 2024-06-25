$(function() {
    const upload_modal_show_btn = $('.btn');
    const modal = $('.modal');
    const init_title_mentions = $('.upload-area-title').text();
    const init_des_mentions = $('.upload-area-description').html();

    upload_modal_show_btn.on('click', function(e) {
        e.preventDefault();

        if (modal.hasClass('up-show')) {
            return;
        }
        modal.addClass('up-show');
    })

    const upload_modal_close_btn = $('.up-close');

    upload_modal_close_btn.on('click', function(e) {
        e.preventDefault();

        if (modal.hasClass('up-show')) {
            modal.removeClass('up-show');
            $('.upload-area-title').text(init_title_mentions);
            $('.upload-area-description').html(init_des_mentions);
        }

        return;
    })

    const upload_area = $('.upload-area');
    const fileInput = $('#fileInput');

    upload_area.on('click', function(e) {
        e.preventDefault();
        fileInput.click();
    });

    upload_area.on('dragover', function(e) {
        e.preventDefault();
        upload_area.addClass('dragover');
    });

    upload_area.on('drop', function(e) {
        e.preventDefault();
        upload_area.removeClass('dragover');
        const files = e.originalEvent.dataTransfer.files;
        fileInput[0].files = files;
        displayFileNames(files);
    })

    fileInput.on('change', function() {
        if (fileInput[0].files.length > 0) {
            const files = fileInput[0].files;
            displayFileNames(files);
        }
    })

    const up_start = $('.up_start');

    up_start.on('click', function(e) {
        e.preventDefault();

        if (!fileInput[0].files.length > 0) {
            alert("파일을 첨부해주세요.");

            return;
        }

        const sendFormData = new FormData(this);
        const sendUrl = this.action;

        fetch(sendUrl, {
            method: 'POST',
            body: sendFormData
        })
        .then(response => {
            if (!response.ok) {
                let error = new Error("Error");
                error.response = response;
                error.status = response.status;
                throw error
            }
            return response.json();
        })
        .then(data => {
            alert(data.success);
            location.href="/main/user/mypage";
        })
        .catch(error => {
            const data = error.response.json();
            data.then(error => alert(error.fail));
        });
    });
});

function displayFileNames(file) {
    const fileName = file[0].name;
    $('.upload-area-title').text(fileName);
    $('.upload-area-description').text('');
}