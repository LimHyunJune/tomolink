$( document ).ready(function() {
    $('#posts').DataTable( {
       ordering:  false,
       pageLength : 7,
       info : false,
       lengthChange : false
    } );

    $('#comments').DataTable( {
           ordering:  false,
           pageLength : 10,
           info : false,
           searching : false,
           lengthChange : false
        } );
});

