/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
   $('.js-toggle').bind('click', function(){
      $('.js-sidebar').toggleClass('is-toggled') ;
      $('.js-conteudo').toggleClass('is-toggled') ;
      $('.js-topbar').toggleClass('is-toggled') ;
      $('.hvr-toggle').blur();
      
   });
});

