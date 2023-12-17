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

    const realUpload = document.getElementById('face_image_input');
    const upload = document.getElementById('face_image_area');
    if(upload != null)
        upload.addEventListener('click', () => realUpload.click());
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e){
             $('#face_image_area').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

$("#face_image_input").change(function(){
   readURL(this);
});
