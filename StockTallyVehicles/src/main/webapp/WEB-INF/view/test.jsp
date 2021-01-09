
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>百度语音测试</title>
    <script type="text/javascript">
        function doTTS(){
            var ttsDiv = document.getElementById('bdtts_div_id');
            var ttsAudio = document.getElementById('tts_autio_id');
            // var ttsText = document.getElementById('ttsText').value;

            // 这样为什么替换不了播放内容
            /*var ssrcc = 'http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd=10&text='+ttsText;
            document.getElementById('tts_source_id').src=ssrcc;*/

            // 这样就可实现播放内容的替换了
            ttsDiv.removeChild(ttsAudio);
            var au1 = '<audio id="tts_autio_id" autoplay="autoplay">';
            var sss = '<source id="tts_source_id" src="layui/music/车辆入厂提示音.mp3" type="audio/mpeg">';
            var eee = '<embed id="tts_embed_id" height="0" width="0" src="">';
            var au2 = '</audio>';
            ttsDiv.innerHTML = au1 + sss + eee + au2;

            ttsAudio = document.getElementById('tts_autio_id');

            ttsAudio.play();
        }
    </script>
</head>
<%

%>
<body >
<div>
    <input type="text" id="ttsText" onchange="doTTS()">
    <input type="button" id="tts_btn"  value="播放">
</div>
<div id="bdtts_div_id">
    <audio id="tts_autio_id" autoplay="autoplay">
        <source id="tts_source_id" src="layui/music/车辆入厂提示音.mp3" type="audio/mpeg">
        <embed id="tts_embed_id" height="0" width="0" src="">
    </audio>
</div>
</body>
</html>