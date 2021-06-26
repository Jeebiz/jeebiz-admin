<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>500报错</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        ul,li{
            list-style: none;
            overflow: hidden;
            margin: 0;
            padding: 0;
        }
        a,a:hover{
            text-decoration: none;
        }
        body{
            margin: 0;
            width: 100%;
        }
        .bg{
            background: url("/assets/images/500bg.jpg") no-repeat center;
            background-size: cover;
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
        }
        .bug{
            position: absolute;
        }
        .bug span{
            color: #333;
            font-size: 1.6rem;
            line-height: 40px;
        }
        .bug p{
            color:#333;
        }
        .bug p a{
            padding-left: 15px;
        }
        .btn-bug{
            background: #007ca1;
            border: none;
            box-shadow: none;
            padding: 10px 20px;
            margin-top: 15px;
            color: #fff;
        }
    </style>
</head>
<body>
<div id="container">

</div>
<div class="bg">
    <div class="bug">
        <span>对不起，你所访问的界面不存在！</span>
        <p>将在<span id="time">10</span>秒后自动转入<a href="${request.contextPath}/" class="btn-bug">网站首页</a> </p>
    </div>
</div>
<script type="text/javascript" src="/webjars/zftal-ui-v5/js/jquery-1.11.1-min.js?ver=${versionUtil()}"></script>
<script type="text/javascript">
    function Load(){
        for(var i=10;i>=0;i--){
            window.setTimeout('doUpdate('+i+')',(10-i)*1000);
        }
    }
    function doUpdate(num){
        document.getElementById("time").innerHTML=num;
        if(num==0){
            window.location= "${request.contextPath}${messageUtil("zftal.home.url")}";
        }
    }
    Load();

    $(function () {
        var bugWidth = $(window).width() - $(".bug").width();
        $(".bug").css('margin-left', bugWidth / 2 +15);
        if ( $(window).width() < 1440 ) {
            $(".bug").css('bottom', '18%');
        } else {
            $(".bug").css('bottom', '22%');
        }
    });
    $(window).resize(function () {
        var bugWidth = $(window).width() - $(".bug").width();
        $(".bug").css('margin-left', bugWidth / 2 +15);
        if ( $(window).width() < 1440 ) {
            $(".bug").css('bottom', '18%');
        } else {
            $(".bug").css('bottom', '22%');
        }
    })
</script>
</body>
</html>