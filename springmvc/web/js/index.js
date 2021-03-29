
var host = "http://localhost:8080/pro/";

function find() {
    var option = $('#schema').val();
    console.log('option: ' + option);

    $.ajax({
        type: 'get',
        url: host + 'app/actor/' + option,
        success: function (data) {
            console.log(data);
        },
        error: function (err) {
            console.warn(err)
        }
    });
}