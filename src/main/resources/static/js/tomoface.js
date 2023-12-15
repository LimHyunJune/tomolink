$( document ).ready(function() {
    const realUpload = document.getElementById('face_image_input');
    const upload = document.getElementById('face_image_area');
    if(upload != null)
        upload.addEventListener('click', () => realUpload.click());

    if(scroll)
    {
        window.scrollTo(window.scrollX,scroll);
    }
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