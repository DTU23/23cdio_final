$(document).ready(function () {

    $('.modal').modal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5,
        complete: function() {
            $(this).find('.preloader-wrapper').addClass('hide');
            $('#userEditTemplate').find('select').material_select('destroy');
        },
        ready: function () {
        }
    });

    $('ul.tabs').tabs();

    initDB();
    
    $(document).on("click", ".edit-user", function (e) {
        var row = $(this).parent().parent();
        template = $('#userEditTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template, {
            "oprId": row.children(".id").text(),
            "oprName":row.children(".name").text(),
            "ini": row.children(".ini").text(),
            "cpr": row.children(".cpr").text(),
            "admin": row.children().find(".admin").is(':checked'),
            "role": row.children(".role").text()
        });
        $('#EditModal').html(rendered).promise().done(function () {
            var select_box = $('#EditModal').find('#user_role');
            var option = 'option[value='+select_box.attr('data-selected')+']';
            select_box.find(option).attr("selected", true);
            $('select').material_select();
            $('#EditModal').modal('open');
        });
    });

    $(document).on("click", '.add-user', function (e) {
        template = $('#userInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template, {"role": "None", "admin": false});
        $('#EditModal').html(rendered).promise().done(function () {
            var select_box = $('#EditModal').find('#user_role');
            var option = 'option[value='+select_box.attr('data-selected')+']';
            select_box.find(option).attr("selected", true);
            $('select').material_select();
        });
        $('#EditModal').modal('open');
        Materialize.updateTextFields();
    });

    $(document).on("click", '.delete-user', function (e) {
        var row = $(this).parent().parent();
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({}),
            url: "./api/v1/operator/"+row.children(".id").text(),
            success: function( response ) {
                if(response){
                    Materialize.toast("User with ID: "+ row.children(".id").text() +" was deleted!", 4000);
                    row.fadeOut("slow", function () {
                        row.remove();
                    });
                }else{
                    Materialize.toast("Unable to delete user!", 4000);
                }
            },
            error: function ( msg ) {
                Materialize.toast("Unable to delete user!", 4000);
            }
        });
    });

    $(document).on("click", ".edit-produce", function (e) {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            processData: false,
            async: false,
            crossDomain: true,
            url: "./api/v1/produce/"+$(this).attr('data-id'),
            headers : {
                Authorization: Cookies.get("auth")
            },
            success: function( response ) {
                data = {};
                data["produce"] = response;
                template = $('#produceEditTemplate').html();
                Mustache.parse(template);   // optional, speeds up future uses
                var rendered = Mustache.render(template, data);
                $('#EditModal').html(rendered).promise().done(function () {
                    $('#EditModal').modal('open');
                });
                return response;
            },
            error: function ( msg ) {
                console.log(msg);
                Materialize.toast("Error in loading data!", 4000);
            }
        });
    });


    $(document).on('click', '.modal-save-produce', function (e) {
        e.preventDefault();
        $('#EditModal').find('.preloader-wrapper').removeClass('hide');
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            processData: false,
            data: {
                produceId: $('#produceEdit').attr('data-source-id'),
                produceName: $('#produceEdit').find('input#produce_name').val(),
                supplier: $('#produceEdit').find('input#produce_supplier').val()
            },
            url: "./api/v1/produce/",
            success: function( msg ) {
                console.log("Response: " + msg);
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update user!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    $(document).on("click", '.add-produce', function (e) {
        template = $('#produceInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template, {"role": "None", "admin": false});
        $('#EditModal').html(rendered).promise().done(function () {
            
        });
        $('#EditModal').modal('open');
        Materialize.updateTextFields();
    });
    
    $(document).on("click", '.delete-produce', function (e) {
        var row = $(this).parent().parent();
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({}),
            url: "./api/v1/operator/"+row.children(".id").text(),
            success: function( response ) {
                if(response){
                    Materialize.toast("User with ID: "+ row.children(".id").text() +" was deleted!", 4000);
                    row.fadeOut("slow", function () {
                        row.remove();
                    });
                }else{
                    Materialize.toast("Unable to delete user!", 4000);
                }
            },
            error: function ( msg ) {
                Materialize.toast("Unable to delete user!", 4000);
            }
        });
    });
    
    $(document).on('click', '.dropdown-item', function (e) {
        $(this).parent().parent().parent().find('a.dropdown-button').html($(this).text());
    });

    $('#loginForm').on('submit', function (e) {
        e.preventDefault();
        // get all the inputs into an array.
        var $inputs = $('#loginForm :input');
        var values = {};
        $inputs.each(function() {
            values[this.name] = $(this).val();
        });
        $.ajax({
            type: "POST",
            data: JSON.stringify({
                "oprId": values.oprId,
                "oprName": "",
                "ini": "",
                "cpr": "",
                "password": values.password,
                "admin": "",
                "role": ""
            }),
            processData: false,
            contentType: "application/json",
            url: "./api/v1/login/",
            success: function( msg ) {
                console.log("Response: " + msg);
                Cookies.set("auth", msg, { expires : 7 });
                $('#loginForm').hide();
                $('main').removeClass('valign-wrapper');
                $('#userAdministration').show();
            },
            error: function ( msg ) {
                Materialize.toast("Invalid login!", 4000);
                console.log(msg);
            }
        });
    });

    $(document).on('click', 'li.tab', function (e) {
        e.preventDefault();
        switch($(this).children("a").attr("href"))
        {
            case "#UserAdminTab":
                populateUsersAdmin(true);
                break;
            case "#RecipeAdminTab":
                break;
            case "#ProduceAdminTab":
                break;
            case "#ProduceAdminSubTab":
                populateProduceAdmin();
                break;
            case "#ProduceBatchAdminSubTab":
                populateProduceBatchAdmin();
                break;
            case "#ProductAdminTab":
                break;
            default:
                break;
        }
    });

    $(document).on('click', '.modal-save-user', function (e) {
        e.preventDefault();
        $('#EditModal').find('.preloader-wrapper').removeClass('hide');
        var role;
        if($('#userEdit').find("#user_role").find('li.active.selected').text() === ''){
            role = $('#userEdit').find('input.select-dropdown').attr('value');
        }else{
            role = $('#userEdit').find("#user_role").find('li.active.selected').text()
        }
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({
                "oprId": $('#userEdit').find("#user_id").val(),
                "oprName": $('#userEdit').find("#user_name").val(),
                "ini": $('#userEdit').find("#user_ini").val(),
                "cpr": $('#userEdit').find("#user_cpr").val(),
                "admin": $('#userEdit').find("#user_admin").is(':checked'),
                "role": role
            }),
            url: "./api/v1/operator/",
            success: function( msg ) {
                console.log("Response: " + msg);
                populateUsersAdmin();
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update user!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    $(document).on('click', '.modal-add-user', function (e) {
        e.preventDefault();
        $('#EditModal').find('.preloader-wrapper').removeClass('hide');
        var role;
        if($('#userAdd').find("#user_role").find('li.active.selected').text() === ''){
            role = $('#userAdd').find('input.select-dropdown').attr('value');
        }else{
            role = $('#userAdd').find("#user_role").find('li.active.selected').text()
        }
        $.ajax({
            type: "POST",
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({
                "oprId": $('#userAdd').find("#user_id").val(),
                "oprName": $('#userAdd').find("#user_name").val(),
                "ini": $('#userAdd').find("#user_ini").val(),
                "cpr": $('#userAdd').find("#user_cpr").val(),
                "admin": $('#userAdd').find("#user_admin").is(':checked'),
                "role": role
            }),
            url: "./api/v1/operator/",
            success: function( msg ) {
                console.log("Response: " + msg);
                populate();
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update user!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    $('.logout').on('click', function (e) {
        e.preventDefault();
        $('#Administration').hide();
        $('#loginForm').show();
        $('main').addClass('valign-wrapper');
        Cookies.remove('auth');
    });

    if(typeof Cookies.get('auth') !== 'undefined'){
        $('#loginForm').hide();
        $('main').removeClass('valign-wrapper');
        $('#Administration').show();
        populateUsersAdmin(false);
        populateProduceAdmin(false);
        //populateProduceBatchAdmin(false);
        //populateProductAdmin(false);
        //populateRecipeAdmin(false);
    }
});

function populateUsersAdmin(notice) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        processData: false,
        crossDomain: true,
        url: "./api/v1/operator/",
        headers : {
            Authorization: Cookies.get("auth")
        },
        success: function( response ) {
            console.log(response);
            var template = $('#UserAdministrationTemplate').html();
            var data = {};
            data['users'] = response;
            Mustache.parse(template);   // optional, speeds up future uses
            var rendered = Mustache.render(template, data);
            $('#UserAdminTab').html(rendered).promise().done(function () {
                $("input:checkbox:not(:checked)").each(function () {
                    $(this).prop("indeterminate", true);
                });
            });
        },
        error: function ( msg ) {
            console.log(msg);
            switch(msg.status){
                case 403:
                    if(notice){
                        Materialize.toast("You are not an admin!", 4000);
                    }
                    $("a[href='#UserAdminTab']").parent().addClass('disabled');
                    break;
            }
        }
    });
}

function populateProduceBatchAdmin(notice) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        processData: false,
        crossDomain: true,
        url: "./api/v1/producebatch/",
        headers : {
            Authorization: Cookies.get("auth")
        },
        success: function( response ) {
            console.log(response);
            var template = $('#ProduceBatchAdministrationTemplate').html();
            var data = {};
            data['producebatch'] = response;
            Mustache.parse(template);   // optional, speeds up future uses
            var rendered = Mustache.render(template, data);

            $('#ProduceBatchAdminSubTab').html(rendered).promise().done(function () {

            });
        },
        error: function ( msg ) {
            console.log(msg);
            Materialize.toast("Error in loading data!", 4000);
        }
    });
}

function populateProduceAdmin(notice) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        processData: false,
        crossDomain: true,
        url: "./api/v1/produce/",
        headers : {
            Authorization: Cookies.get("auth")
        },
        success: function( response ) {
            console.log(response);
            var template = $('#ProduceAdministrationTemplate').html();
            var data = {};
            data['produce'] = response;
            Mustache.parse(template);   // optional, speeds up future uses
            var rendered = Mustache.render(template, data);

            $('#ProduceAdminSubTab').html(rendered).promise().done(function () {

            });
        },
        error: function ( msg ) {
            console.log(msg);
            if(notice){
                Materialize.toast("Error in loading data!", 4000);
            }
        }
    });
}

function populateProductAdmin(notice) {

}

function populateRecipeAdmin(notice){

}

function initDB() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        processData: false,
        url: "./api/v1/database/init",
        success: function( response ) {
            console.log(response);
        },
        error: function ( msg ) {
            console.log(msg);
            Materialize.toast("Error initializing database!", 4000);
        }
    });
}