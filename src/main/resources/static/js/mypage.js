$( document ).ready(function() {
    $('#message').DataTable( {
       ordering:  true,
       order: [[2, 'desc']],
       pageLength : 5,
       info : false,
       searching : false,
       lengthChange : false
    });

    $('#post').DataTable( {
       ordering:  true,
       order: [[2, 'desc']],
       pageLength : 5,
       info : false,
       searching : false,
       lengthChange : false
    } );

    $('#comments').DataTable( {
           ordering:  true,
           order: [[2, 'desc']],
           pageLength : 5,
           info : false,
           searching : false,
           lengthChange : false
    });
});


