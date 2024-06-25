$(function() {
    var toggle = document.querySelectorAll('.toggle')
    toggle.forEach(function(type) {
        type.addEventListener('click', function() {
            var childrenElement = this.parentElement.children;
            var ripple = Array.from(childrenElement).find(child => child.classList.contains('ripple'));
            var input = Array.from(childrenElement).find(child => child.tagName.toLowerCase() === 'input');

            ripple.classList.add('animate');
            input.classList.add('animate');
            input.type === 'text' ? input.type = 'password' : input.type = 'text';
            this.dataset.state = input.type === 'text' ? 'visible' : 'hidden';
        });
    });

    var ripple = document.querySelectorAll('.toggle');
    ripple.forEach(function(type) {
        type.classList.remove('animate');
    });

    var input = document.querySelectorAll('input');
    input.forEach(function(type) {
        type.classList.remove('animate');
    });

    var toggle = document.querySelectorAll('.toggle');
    toggle.forEach(function(type) {
        type.style.pointerEvents = 'all';
    });

    const pass_Form = $('#pass-form');

    pass_Form.submit(function(e) {
        e.preventDefault();

        const current_Pw = $('#current_pw').val();
        const new_Pw = $('#new_pw').val();
        const confirm_Pw = $('#confirm_pw').val();

        if (new_Pw != confirm_Pw) {
            alert("새로운 패스워드가 일치하지 않습니다. 확인해주세요.");

            return;
        }

        if (new_Pw.indexOf(' ') !== -1 || confirm_Pw.indexOf(' ') !== -1) {
            alert("패스워드에 공백은 포함될 수 없습니다.");

            return;
        }

        if (new_Pw.trim() === "" || confirm_Pw.trim() === "") {
            alert("패스워드를 빈 값으로 둘 수 없습니다.");

            return;
        }

        if (current_Pw === new_Pw) {
            alert("이전에 사용하던 패스워드와 동일한 패스워드는 사용할 수 없습니다.");

            return;
        }

        const sendUrl = this.action;
        fetch(sendUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: $('#pass-form').serialize()
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
    })
});