
var host = window.location.origin + "/pro/";

function find() {
    var preview = $('#preview').is(':checked');
    var option = $('#schema').val();
    console.log('option: ' + option + ', preview: ' + preview);
    if (option === 'default' || !preview)
        return;

    $.ajax({
        type: 'get',
        url: host + option + '/list/1',
        success: function (data) {
            data = eval(data);
            showroom(data);
        },
        error: function (err) {
            console.warn(err)
        }
    });
}

function showroom(data) {
    if (data.length < 1) {
        empty_data();
        return;
    }

    var $showroom = $('#showroom');
    $showroom.empty();

    var heads = create_head(data[0]);
    var rows = create_row(data);
    $showroom.append(heads.join(""));
    $showroom.append(rows.join(""));
}

function empty_data() {
    $('#tips').text('No data');
}

function create_head(row) {
    var keys = Object.keys(row);
    var heads = [];
    heads.push("<tr>");
    for (var i in keys)
        heads.push("<th>" + keys[i] + "</th>");
    heads.push("</tr>");
    return heads;
}

function create_row(data) {
    var rows = [];
    for (var i in data) {
        var keys = Object.keys(data[i]);
        var row = [];
        row.push("<tr>");
        for (var j in keys)
            row.push("<td>" + data[i][keys[j]] + "</td>");
        row.push("</tr>");
        rows.push(row);
    }
    return rows;
}

function create_object(button) {
    $(button).attr('disabled', true);
    $('#create_form').show();
}

function create() {
    var first_name = $('#first_name').val();
    var last_name = $('#last_name').val();
    if (first_name.length < 1) {
        alert('No allow empty first_name');
        return;
    }
    if (last_name.length < 1) {
        alert('No allow empty last_name');
        return;
    }

    $.ajax({
        type: 'post',
        url: host + 'trace/actor?first_name=' + first_name + '&last_name=' + last_name,
        success: function (data) {
            cancel_create();
            var array = [];
            array.push(data);
            showroom(array);
            $('#create_form')[0].reset();
        },
        error: function (err) {
            console.warn(err);
            $('#create_button').attr('disabled', false);
        }
    });
}

function cancel_create() {
    $('#create_form').hide();
    $('#create_button').attr('disabled', false);
}

function upload_file(button) {
    $(button).attr('disabled', true);
    $('#upload_form').show();
}

function cancel_upload() {
    $('#upload_button').attr('disabled', false);
    $('#upload_form').hide();
}