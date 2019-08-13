$(() => {
  
  $('#guest, .close-modal').on('click', () => {
    $('.login-modal, .overlay').fadeOut();
  });
  
  $('#login').on('click', () => {
    $('.modal-content').slideToggle();
    $('.username').focus();
  });
  
  $('.register-link').on('click', () => {
    $('.register-slide').addClass('active-register');
    $('.login-form').addClass('move-form');
  });
  
  $('.close-register').on('click', () => {
    $('.register-slide').removeClass('active-register');
    $('.login-form').removeClass('move-form');
    $('.username').focus();
  });
  
});