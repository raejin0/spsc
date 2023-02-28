// $(function() {
//     var onScroll = function() {
//         var y = $(document).scrollTop();
//         $('.hover-menu').css('top', (y<60?90-y:10)+'px');
//     };
//     $('body').scrollspy({target: '.hover-menu'});
//
//     $(document).on('scroll', onScroll);
//     onScroll();
// });
//
// (function() {
//     var wnd = $(window), menu = $('.hover-menu'), base = $('.mount-container');
//     wnd.on('resize', function() {
//         var pad = 20, left = (base.offset().left-menu.width()-pad)/2;
//         menu.css('left', left+'px');
//         menu.css('display', left<pad? 'none': 'block');
//     });
//     wnd.trigger('resize');
// })();

$('.modal .cmd-close').click(function(e) {
    e.preventDefault();
    $('.modal').modal('hide');
});

// Lightbox Effect

var fb3d = {
    activeModal: undefined,
    capturedElement: undefined
};

(function() {
    function findParent(parent, node) {
        while(parent && parent!=node) {
            parent = parent.parentNode;
        }
        return parent;
    }
    $('body').on('mousedown', function(e) {
        fb3d.capturedElement = e.target;
    });
    $('body').on('click', function(e) {
        if(fb3d.activeModal && fb3d.capturedElement===e.target) {
            if(!findParent(e.target, fb3d.activeModal[0]) || findParent(e.target, fb3d.activeModal.find('.cmd-close')[0])) {
                e.preventDefault();
                fb3d.activeModal.fb3dModal('hide');
            }
        }
        delete fb3d.capturedElement;
    });
})();
$('body').on('keydown', function(e) {
    if(fb3d.activeModal && e.keyCode===27) {
        e.preventDefault();
        fb3d.activeModal.fb3dModal('hide');
    }
});

$.fn.fb3dModal = function(cmd) {
    setTimeout(function() {
        function fb3dModalShow() {
            if(!this.hasClass('visible')) {
                $('body').addClass('fb3d-modal-shadow');
                this.addClass('visible');
                fb3d.activeModal = this;
                this.trigger('fb3d.modal.show');
            }
        }
        function fb3dModalHide() {
            if(this.hasClass('visible')) {
                $('body').removeClass('fb3d-modal-shadow');
                this.removeClass('visible');
                fb3d.activeModal = undefined;
                this.trigger('fb3d.modal.hide');
            }
        }
        var mdls = this.filter('.fb3d-modal');
        switch(cmd) {
            case 'show':
                fb3dModalShow.call(mdls);
                break;
            case 'hide':
                fb3dModalHide.call(mdls);
                break;
        }
    }.bind(this), 50);
};
