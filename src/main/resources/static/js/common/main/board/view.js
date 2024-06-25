$(function() {
    const boardId = $('#boardId');
    const commentId = $('#commentId');
    const dropdownElementList = $('.drop-down-id');
    let dropdownList = [];

    dropdownElementList.each(function(index, dropdownToggleEl) {
        $(dropdownToggleEl).on('click', function() {
            const dropdown = new bootstrap.Dropdown(dropdownToggleEl);
            dropdown.toggle();
        })
    });

    $('body').on('click', function(event) {
        if (!$(event.target).hasClass('drop-down-id')) {
            $('.drop-down-id').each(function(index, dropdownToggleEl) {
                const dropdown = bootstrap.Dropdown.getInstance(dropdownToggleEl);
                if (dropdown && dropdown._element !== event.target) {
                    dropdown.hide();
                }
            });
        }
    });

    $(document).on('keydown', function(event) {
        if (event.key === 'Escape') {
            $('.drop-down-id').each(function(index, dropdownToggleEl) {
                const dropdown = bootstrap.Dropdown.getInstance(dropdownToggleEl);
                if (dropdown) {
                    dropdown.hide();
                }
            });
        }
    });

    const bd_modify = $('.bd-modify');
    const bd_delete = $('.bd-delete');

    bd_modify.on('click', function(e) {
        e.preventDefault();
        location.href='/main/board/modify/' + boardId.val();
    })

    bd_delete.on('click', function(e) {
        e.preventDefault();

        fetch('/main/board/del/' + boardId.val(), {})
        .then(response => {
            if (!response.ok) {
                let error = new Error("Error");
                error.response = response;
                throw error
            }
            return response.json();
        })
        .then(data => {
            alert(data.success);
            location.href="/main/board/list";
        })
        .catch(error => {
            const data = error.response.json();
            data.then(error => alert(error.fail));
        });
    })

    const bd_comment_save = $('.bd-comment-save');

    bd_comment_save.on('click', function(e) {
        e.preventDefault();
        input__field = $('.input__field').val().trim();

        if (input__field === '') {
            alert("댓글 작성 시 공백만 입력할 수 없습니다.");

            return;
        }

        let formData = {
            commentText: input__field,
            boardId: boardId.val()
        }

        fetch('/main/board/comment', {
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
                throw error
            }
            return response.json();
        })
        .then(data => {
            alert(data.success);
            location.reload();
        })
        .catch(error => {
            const data = error.response.json();
            data.then(error => alert(error.fail));
        });
    })

    const bd_comment_cancel = $('.bd-comment-cancel');

    bd_comment_cancel.on('click', function(e) {
        e.preventDefault();
        cmt_cancel();
    })

    const bd_comment_modify = $('.bd-comment-modify');

    bd_comment_modify.on('click', function(e) {
        e.preventDefault();
        input__field = $('.input__field').val().trim();

        if (input__field === '') {
            alert("댓글 수정 시 공백만 입력할 수 없습니다.");

            return;
        }

        let formData = {
            Id: commentId.val(),
            boardId: boardId.val(),
            commentText: input__field
        }

        fetch('/main/board/comment/modify', {
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
                throw error
            }
            return response.json();
        })
        .then(data => {
            alert(data.success);
            location.reload();
        })
        .catch(error => {
            const data = error.response.json();
            data.then(error => alert(error.fail));
        });
    })

    const bd_comment_reset = $('.bd-comment-reset');

    bd_comment_reset.on('click', function(e) {
        e.preventDefault();
        $('.input__field').val("");
    });
})

// 댓글 수정 셋팅
function cmt_modify(cmt_id) {
    let comment_text_list = [];
    const input_field = $('.input__field');
    const comment_texts = $('.bd-comment-text');
    const button_group = $('.button-group');
    input_field[0].scrollIntoView({ behavior: 'smooth' });
    $('#commentId').val(cmt_id + 1);

    comment_texts.each(function(index, txt) {
        if (cmt_id === index) {
            input_field.val($(txt).text());

            return
        }
    });

    if (button_group.eq(0).hasClass('show')) {
        button_group.eq(0).removeClass('show');
        button_group.eq(1).addClass('show');
    }
}

// 댓글 수정 취소
function cmt_cancel() {
    const button_group = $('.button-group');
    $('#commentId').val("");
    $('.input__field').val("");

    if (button_group.eq(1).hasClass('show')) {
        button_group.eq(1).removeClass('show');
        button_group.eq(0).addClass('show');
    }
}

function cmt_del(cmt_id) {
    const boardId = $('#boardId');

    let formData = {
        Id: cmt_id + 1,
        boardId: boardId.val()
    }

    fetch('/main/board/comment/del', {
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
            throw error
        }
        return response.json();
    })
    .then(data => {
        alert(data.success);
        location.reload();
    })
    .catch(error => {
        const data = error.response.json();
        data.then(error => alert(error.fail));
    });
}

function serializeParams(data) {
    return Object.keys(data).map(key => key + '=' + encodeURIComponent(data[key])).join('&');
}