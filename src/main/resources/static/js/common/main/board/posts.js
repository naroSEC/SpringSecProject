$(function() {
    $('.editor-toolbar button').prop('disabled', true);
    const postsSave = $('.button--save');
    const postsTitle = $('#postsTitle');
    const postsContent = $('#postsContent');
//    const _csrf = $('input[name="_csrf"]');

    postsSave.on('click', function(e) {
        e.preventDefault();

        if (postsTitle.val() === '' || postsContent.val() === '') {
            alert('게시글 작성 시 제목 또는 내용을 빈 값으로 둘 수 없습니다.');

            return;
        }

        let formData = {
            postsTitle: postsTitle.val(),
            postsContent: postsContent.val(),
//            _csrf: _csrf.val()
        }

        fetch('/main/board/write', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: serializeParams(formData)
        })
        .then(response => response.json())
        .then(data => {
            alert(data.success);
            location.href="/main/board/list";
        })
        .catch((error) => {
            alert(error.fail);
        });
    })

    const postsModify = $('.button--modify');
    const postsIdx = $('#postsIdx');

    postsModify.on('click', function(e) {
        e.preventDefault();

        if (postsTitle.val() === '' || postsContent.val() === '') {
            alert('게시글 작성 시 제목 또는 내용을 빈 값으로 둘 수 없습니다.');

            return;
        }

        let formData = {
            Id: postsIdx.val(),
            postsTitle: postsTitle.val(),
            postsContent: postsContent.val()
        }

        fetch('/main/board/modify', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: serializeParams(formData)
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
            location.href="/main/board/view/" + postsIdx.val();
        })
        .catch(error => {
            const data = error.response.json();
            data.then(error => alert(error.fail));
        });
    })

    const postsCancel = $('.button--cancel');

    postsCancel.on('click', function(e) {
        e.preventDefault();
        location.href='/main/board/view/' + postsIdx.val();
    })

})

function serializeParams(data) {
    return Object.keys(data).map(key => key + '=' + encodeURIComponent(data[key])).join('&');
}