$(function() {
    $('.nav-link').eq(0).removeClass('active');
    $('.nav-link').eq(0).addClass('text-white');
    $('.nav-link').eq(1).addClass('active');

    const FORM = document.querySelector("form");
    const TYPES = ["input[type=text]", "input[type=submit]", "input[type=email]", "textarea"];
    const FOCUS = document.getElementById("focus");

    function position(element) {

      var props = {
        top: element.offsetTop,
        left: element.offsetLeft,
        width: element.offsetWidth,
        height: element.offsetHeight,
        radius: parseInt(window.getComputedStyle(element).borderRadius)
      };

      FOCUS.style.top = props.top + "px";
      FOCUS.style.left = props.left + "px";
      FOCUS.style.width = props.width + "px";
      FOCUS.style.height = props.height + "px";
      FOCUS.style.borderRadius = props.radius + "px";

      FOCUS.style.display = "block";
    }

    TYPES.forEach(function(type) {
      FORM.querySelectorAll(type).forEach(function(input) {
        input.addEventListener("focus", function() {

          window.addEventListener("resize", function() {
            position(input);
          });

          position(input);
        });
      });
    });

    FORM.addEventListener("focusout", function(e) {
      setTimeout(function() {
        if (!FORM.contains(document.activeElement)) {
          FOCUS.style.display = "none";
        }
      }, 0);
    });

    const mp_info_save = $('.mp-info-save');

    mp_info_save.on('click', function(e) {
        e.preventDefault();

        const email = $('.mp-input-email').val().trim();
        const introduce = $('.mp-input-intro').val().trim();

        if (email === '' || introduce === '') {
            alert("이메일과 자기소개 입력 폼은 빈 값으로 둘 수 없습니다.");

            return;
        }

        fetch('/main/user/mypage/info', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: $('.mp-input-form').serialize()
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
            location.href="/main/user/mypage/info";
        })
        .catch(error => {
            const data = error.response.json();
            data.then(error => alert(error.fail));
        });
    });
});