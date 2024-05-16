window.onload = function(){
    function showAlert(alertTitle, alertText, alertIcon, alertConfirmButtonText) {
        Swal.fire({
            title: alertTitle,
            text: alertText,
            icon: alertIcon,
            confirmButtonText: alertConfirmButtonText
        });
    }


    document.querySelector(".goback").addEventListener("click" , function (){
        location.href = '/landing';

    });
    document.querySelector(".id").addEventListener("input" , function (){
        $(".hidden").val("false");

    });

    document.querySelector(".regist").addEventListener("click" , function (){
        let id = $(".id").val();
        let pwd1 = $(".pwd1").val();
        let pwd2 = $(".pwd2").val();
        let dup = $(".hidden").val();

        if(dup == 'false'){
            showAlert("경고!","아이디 중복확인을 하세요.","warning","확인");
            // alert('아이디 중복확인을 하세요.')
            return;
        }

        if(pwd1 != pwd2 ){
            showAlert("경고!","비밀번호가 일치하지 않습니다. 다시 입력해 주세요.","error","확인");
            // alert("비밀번호가 일치하지 않습니다. 다시 입력해 주세요.");
            $(".pwd1").val("");
            $(".pwd2").val("");
            return;
        }

        const form = document.createElement('form');
        form.setAttribute('method' , 'post');
        form.setAttribute('action' , '/register');


        const data_1 = document.createElement('input');
        data_1.setAttribute('type' , 'hidden');
        data_1.setAttribute('name' , 'cmd');
        data_1.setAttribute('value','addmember');


        const data_2 = document.createElement('input');
        data_2.setAttribute('type' , 'hidden');
        data_2.setAttribute('name' , 'id');
        data_2.setAttribute('value',id);

        const data_3 = document.createElement('input');
        data_3.setAttribute('type' , 'hidden');
        data_3.setAttribute('name' , 'pwd');
        data_3.setAttribute('value',pwd1);

        form.appendChild(data_1);
        form.appendChild(data_2);
        form.appendChild(data_3);

        document.body.appendChild(form);
        form.submit();


    });




    document.querySelector(".signup-item2-duple").addEventListener("click" , function (){
        let id = $(".id").val();
        let cmd = "dupcheck";
        let result = "true";
        if(id == "" || id == null){
            // alert("아이디를 입력해주세요.");
            showAlert("경고!","아이디를 입력해주세요.","warning","확인");
            return;
        }


        $.ajax({
            type:"post" ,
           url:"/register" ,
           async:true,
            dataType:'text' ,
            data:{ id : id , cmd: cmd },
            success: function (result){
                console.log(result);
                if(result == "false"){
                    // alert("아이디가 중복됩니다 다른 아이디를 이용해 주세요.");
                    showAlert("경고!","아이디가 중복됩니다. 다른 아이디를 이용해 주세요.","error","확인");
                }else {
                    showAlert("성공!","아이디를 사용할 수 있습니다.","success","확인");
                    // alert("아이디를 사용할수 있습니다.");
                    $(".hidden").val("true");
                }
            } ,
            error : function() {}
        });


    });




};