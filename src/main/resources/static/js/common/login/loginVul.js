$(function() {
    let container = document.getElementById('container');

    toggle = () => {
        container.classList.toggle('sign-in')
        container.classList.toggle('sign-up')
    }

    setTimeout(() => {
        container.classList.add('sign-in')
    }, 200);

    const show_btn = $('#show-btn')
    const passwd_input = $('#passwd-field')
    let input = $('#passwd-input')



    show_btn.click(function(e) {
        e.preventDefault();

        if (passwd_input.hasClass("show")) {
            passwd_input.removeClass("show");
            passwd_input.removeAttr("style");
            $('.top').attr("d", "M2 10.5C2 10.5 6.43686 5.5 10.5 5.5C14.5631 5.5 19 10.5 19 10.5");

            return
        }
        passwd_input.addClass("show");
        passwd_input.css({
             "--eye-x": "0px",
             "--eye-y": "0px",
             "--eye-background": "1",
             "--eye-s": "0",
             "--eye-offset": "0px"
        });
        $('.top').attr("d", "M2 10.5C2 10.5 6.43686 15.5 10.5 15.5C14.5631 15.5 19 10.5 19 10.5");
        $('.clear').val($('#passwd-input').val())
    })

    const signIn_Btn = $('#signIn-form');

    $("#id-input, #passwd-input").keydown(function(e){
        if (e.keyCode == 13) {
            signIn_Btn.submit();
        }
    });

    $('.input-field').keydown(function(e){
        if (e.keyCode == 13) {
            return false;
        }
    });

    signIn_Btn.submit(function(e) {
        e.preventDefault();

        const userId = $('#id-input').val();
        const userPw = $('#passwd-input').val();

        if (userId.indexOf(' ') !== -1 || userPw.indexOf(' ') !== -1) {
            alert("아이디와 패스워드에는 공백이 포함될 수 없습니다.")

            return;
        }
        if (userId.trim() === "" || userPw.trim() === "") {
            alert("아이디와 패스워드는 빈 값으로 둘 수 없습니다.")

            return;
        }

        fetch('/api/loginVulProc', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: $('#signIn-form').serialize()
        })
        .then(res => {
            if (res.redirected) {
                window.location.href = res.url;
            }

            const resJson = res.json();
            if (!res.ok) {
                resJson.then(data => {
                    alert(data.fail);
                })
            }
        })
    });

    const signUp_Btn = $('#signUp-form')

    $('#userId, #userPw, #email, #userPw, #confirmPw').keydown(function(e){
        if (e.keyCode == 13) {
            signUp_Btn.submit();
        }
    });

    signUp_Btn.submit(function(e) {
        e.preventDefault();

        if (!(emptyCheck() && validPwCheck() && koreanCheck() && lengthCheck())) {

            return;
        }

        if (!$("input[name='role']:checked").val()) {
            alert("계정 권한을 선택해주세요.");

            return;
        }

        const formData = {};
        $('#signUp-form').serializeArray().forEach(key => {
            formData[key.name] = key.value;
        });

        fetch('/api/CreateAccount', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            alert(data.success);
            location.reload();
        })
        .catch((error) => {
            alert(error.fail);
        });
    })
});

function validPwCheck() {
    const userPw = $('#userPw').val().trim();
    const confirmPw = $('#confirmPw').val().trim();

    if (userPw != confirmPw) {
        alert("패스워드가 일치하지 않습니다.");

        return false;
    }

    return true;
}

function emptyCheck() {
    const checkParams = ['#userId', '#userPw', '#confirmPw', '#userName', '#email'];

    checkParams.forEach(inputId => {
        const value = $(inputId).val().trim();
        if (value === '') {
            alert("회원가입 입력 폼은 빈 값으로 둘 수 없습니다.");

            return false;
        }

        if (value.indexOf(' ') !== -1) {
            alert("문자열에 공백은 포함 될 수 없습니다.");

            return false;
        }
    });

    return true;
}

function koreanCheck() {
    const userName = $('#userName').val();
    const regex = /^[가-힣]*$/;

    if (regex.test(userName)) {
        return true;
    }

    alert("사용자 이름은 한글만 입력 가능합니다.");
    return false;
}

function lengthCheck() {
    const userId = $('#userId').val().trim();
    const userPw = $('#userPw').val().trim();

    if (!(userId.length >= 4 && userId.length <= 12)) {
        alert("사용자 아이디는 4자 이상 10자 이하만 사용 가능합니다.");

        return false;
    }

    if (!(userPw.length >= 4 && userPw.length <= 16)) {
        alert("사용자 비밀번호는 4자 이상 16자 이하만 사용 가능합니다.")

        return false;
    }

    return true;
}