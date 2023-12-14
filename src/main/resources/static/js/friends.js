$( document ).ready(function() {
    $('#posts').DataTable( {
       ordering:  true,
       order: [[3, 'desc']],
       pageLength : 7,
       info : false,
       lengthChange : false
    } );

    $('#comments').DataTable( {
           ordering:  true,
           order: [[2, 'desc']],
           pageLength : 10,
           info : false,
           searching : false,
           lengthChange : false
        } );

    if(scroll)
    {
        window.scrollTo(window.scrollX,scroll);
    }
});


function setCookie(name, value = "", expireDays = "", path = "/") {
  let expires = "";
  if (expireDays) {
    let date = new Date();
    date.setTime(date.getTime() + expireDays * 24 * 60 * 60 * 1000);
    expires = `expires=${date.toUTCString()};`;
  }
  document.cookie = `${name}=${value || ""};${expires}path=${path}`;
}

function friendSearchCommentsSecretSubmit()
{
    setCookie("scroll", window.scrollY);
    $('#secret_submit').submit();
}

function friendSearchCommentsNoSecretSubmit()
{
    setCookie("scroll", window.scrollY);
    $('#no_secret_submit').submit();
}
