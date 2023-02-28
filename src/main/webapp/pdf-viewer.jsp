<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>전자책 교안</title>
    <link rel="stylesheet" href="<c:url value="/static/3d-flip-book/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/3d-flip-book/css/style.css"/>">
    <script src="<c:url value="/static/3d-flip-book/js/jquery.min.js"/>"></script>

</head>
<body>
<div class="modal fade">
    <div class="modal-dialog">
        <a href="#" class="cmd-close"><span class="glyphicon glyphicon-remove"></span></a>
        <div class="modal-content">

        </div>
    </div>
</div>


<div class="fb3d-modal light visible">
    <a href="#" class="cmd-close"><span class="glyphicon glyphicon-remove"></span></a>
    <div class="mount-container"><iframe style="border: 0; width: 100%; height: 100%" scrolling="no"></iframe></div>
</div>


<script src="<c:url value="/static/3d-flip-book/js/three.min.js"/>"></script>
<script type="text/javascript">
    window.PDFJS_LOCALE = {
        pdfJsWorker: '<c:url value="/static/3d-flip-book/js/pdf.worker.js"/>',
        pdfJsCMapUrl: 'cmaps'
    };
</script>

<script src="<c:url value="/static/3d-flip-book/js/pdf.min.js"/>"></script>
<script src="<c:url value="/static/3d-flip-book/js/3dflipbook.min.js"/>"></script>
<script src="<c:url value="/static/3d-flip-book/js/script.js"/>"></script>

<script type="text/javascript">

    // 페이지 초기화
    $(document).ready(function(){

        $('.mount-container').FlipBook({
            pdf: '<c:url value="/api/file/download/${attachmentId}/${fileSn}"/>',
            template: { // by means this property you can choose appropriate skin
                html: '<c:url value="/static/3d-flip-book/templates/default-book-view.html"/>',
                styles: [
                    '<c:url value="/static/3d-flip-book/css/black-book-view.css"/>'// or one of white-book-view.css, short-white-book-view.css, shart-black-book-view.css
                ],
                links: [{
                    rel: 'stylesheet',
                    href: '<c:url value="/static/3d-flip-book/css/font-awesome.min.css"/>'
                }],
                script: '<c:url value="/static/3d-flip-book/js/default-book-view.js"/>',
                // printStyle: undefined, // or you can set your stylesheet for printing ('print-style.css')
                // sounds: {
                //     startFlip: 'sounds/start-flip.mp3',
                //     endFlip: 'sounds/end-flip.mp3'
                // }
            },
            //template: {"html":"templates\/default-book-view.html","styles":["css\/font-awesome.min.css","css\/black-book-view.css"],"script":"js\/default-book-view.js","sounds":{"startFlip":"sounds\/start-flip.mp3","endFlip":"sounds\/end-flip.mp3"}},
            controlsProps: {
                // downloadURL: 'books/pdf/1.pdf',
                downloadURL: '<c:url value="/api/file/download/${attachmentId}/${fileSn}"/>',
                actions: {
                    cmdBackward: {
                        code: 37,
                    },
                    cmdForward: {
                        code: 39
                    },
                    cmdSave: {
                        code: 68,
                        flags: 1,
                    }
                },
                loadingAnimation: {
                    book: false
                },
                autoResolution: {
                    enabled: false
                },
                scale: {
                    max: 4
                }
            },
            ready: function(scene) {
                scene.ctrl.cmdToc();
            }
        });

    });

</script>

</body>
</html>