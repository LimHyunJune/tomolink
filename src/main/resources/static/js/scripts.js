$( document ).ready(function() {
});


$('#submit').on('click',function(){
    var data = {

    }
    $.ajax({
        type: "POST",
        dataType: "jsonp",
        data : data,
        url: "http://localhost:5000/predict",
        success: function (data) {

        }
    });
})