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

    $('#MainTabs').tabs();
    $('#ProduceSubTabs').tabs();

    /**
     * ProductBatch Listeners
     */
    // Recipe Opening/closing
    $(document).on('click', '.product.collapsible-header', function (e) {
        var TargetId = $(this).attr('data-id');
        $.ajax({
            context: TargetId,
            type: "GET",
            contentType: "application/json",
            processData: false,
            crossDomain: true,
            url: "./api/v1/product/list/"+$(this).attr('data-id'),
            headers : {
                Authorization: Cookies.get("auth")
            },
            success: function( response ) {
                console.log(response);
                var template = $('#productBatchComponentsTemplate').html();
                var data = {};
                data['productbatchcomponents'] = response;
                data['pbId'] = TargetId;
                Mustache.parse(template);   // optional, speeds up future uses
                var rendered = Mustache.render(template, data);

                $('div[data-id='+TargetId+']').parent().children('.collapsible-body').html(rendered).promise().done(function () {
                    console.log($('div[data-id='+TargetId+']').parent().children('.collapsible-body'));
                });
            },
            error: function ( msg ) {
                console.log(msg);
                Materialize.toast("Error in loading data!", 4000);
            }
        });
    });

    $(document).on("click", '.add-recipecomp', function (e) {
        template = $('#recipeComponentInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        data = {};
        data["recipeId"] = $(this).attr('data-recipe-id');
        console.log(data);
        var rendered = Mustache.render(template, data);
        $('#EditModal').html(rendered).promise().done(function () {

        });
        $('#EditModal').modal('open');
    });

    $(document).on("click", '.add-productbatch', function (e) {
        template = $('#productBatchInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template);
        $('#EditModal').html(rendered).promise().done(function () {

        });
        $('#EditModal').modal('open');
        Materialize.updateTextFields();
    });

    $(document).on('click', '.modal-add-productbatch', function (e) {
        e.preventDefault();
        console.log($('#productBatchAdd').find('#recipe_id').val());
        $.ajax({
            type: "POST",
            context: $('#productBatchAdd').find('#recipe_id').val(),
            processData: false,
            contentType: "text/plain",
            data: $('#productBatchAdd').find('#recipe_id').val(),
            headers : {
                Authorization: Cookies.get("auth")
            },
            url: "./api/v1/product/",
            success: function( msg ) {
                console.log("Response: " + msg);
                populateProductAdmin(false);
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update product batch!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    /**
     * ProductBathcComp Listeners
     */


    /**
     * User Listeners
     */
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

    $(document).on("click", '.delete-user', function (e) {
        var row = $(this).parent().parent();
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            processData: false,
            headers : {
                Authorization: Cookies.get("auth")
            },
            data: JSON.stringify({}),
            url: "./api/v1/operator/"+row.children(".id").text(),
            success: function( response ) {
                Materialize.toast("User with ID: "+ row.children(".id").text() +" was deleted!", 4000);
                populateUsersAdmin(false);
            },
            error: function ( msg ) {
                Materialize.toast("Unable to delete user!", 4000);
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
            headers : {
                Authorization: Cookies.get("auth")
            },
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
                populateUsersAdmin(false);
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update user!", 4000);
                $('#EditModal').modal('close');
            }
        });
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
            headers : {
                Authorization: Cookies.get("auth")
            },
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

    /**
     * Produce Listeners
     */
    $(document).on("click", '.add-produce', function (e) {
        template = $('#produceInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template, {"role": "None", "admin": false});
        $('#EditModal').html(rendered).promise().done(function () {

        });
        $('#EditModal').modal('open');
        Materialize.updateTextFields();
    });

    $(document).on("click", ".edit-produce", function (e) {
        var TargetId = $(this).attr('data-id');
        $.ajax({
            type: "GET",
            context: TargetId,
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
                data["produceId"] = TargetId;
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

    $(document).on("click", '.delete-produce', function (e) {
        $.ajax({
            type: "DELETE",
            processData: false,
            headers : {
                Authorization: Cookies.get("auth")
            },
            url: "./api/v1/operator/"+$(this).attr('data-id'),
            success: function( response ) {
                Materialize.toast("User with ID: "+ row.children(".id").text() +" was deleted!", 4000);
                populateProduceAdmin(false);
            },
            error: function ( msg ) {
                Materialize.toast("Unable to delete produce!", 4000);
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
            headers : {
                Authorization: Cookies.get("auth")
            },
            data: JSON.stringify({
                produceId: $('#produceEdit').attr('data-source-id'),
                produceName: $('#produceEdit').find('input#produce_name').val(),
                supplier: $('#produceEdit').find('input#produce_supplier').val()
            }),
            url: "./api/v1/produce/",
            success: function( msg ) {
                populateProduceAdmin(false);
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update user!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    $(document).on('click', '.modal-add-produce', function (e) {
        e.preventDefault();
        $('#EditModal').find('.preloader-wrapper').removeClass('hide');
        $.ajax({
            type: "POST",
            contentType: "application/json",
            processData: false,
            headers : {
                Authorization: Cookies.get("auth")
            },
            data: JSON.stringify({
                produceName: $('#produceAdd').find('input#produce_name').val(),
                supplier: $('#produceAdd').find('input#produce_supplier').val()
            }),
            url: "./api/v1/produce/",
            success: function( msg ) {
                console.log("Response: " + msg);
                populateProduceAdmin(false);
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update user!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    /**
     * Producebatch Listeners
     */

    $(document).on("click", '.add-producebatch', function (e) {
        template = $('#produceBatchInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template);
        $('#EditModal').html(rendered).promise().done(function () {

        });
        $('#EditModal').modal('open');
        Materialize.updateTextFields();
    });

    $(document).on("click", ".edit-producebatch", function (e) {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            processData: false,
            async: false,
            crossDomain: true,
            url: "./api/v1/producebatch/"+$(this).attr('data-rb-id'),
            headers : {
                Authorization: Cookies.get("auth")
            },
            success: function( response ) {
                console.log(response);
                data = {};
                data["producebatch"] = response;
                template = $('#produceBatchEditTemplate').html();
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

    $(document).on('click', '.modal-save-producebatch', function (e) {
        e.preventDefault();
        $('#EditModal').find('.preloader-wrapper').removeClass('hide');
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            processData: false,
            headers : {
                Authorization: Cookies.get("auth")
            },
            data: JSON.stringify({
                rbId: $('#produceBatchEdit').attr('data-source-id'),
                produceName: null,
                supplier: null,
                produceId: $('#produceBatchEdit').find('input#produceId').val(),
                amount: $('#produceBatchEdit').find('input#amount').val()
            }),
            url: "./api/v1/producebatch/",
            success: function( msg ) {
                populateProduceBatchAdmin(false);
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update produce batch!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    $(document).on('click', '.modal-add-producebatch', function (e) {
        e.preventDefault();
        $('#EditModal').find('.preloader-wrapper').removeClass('hide');
        $.ajax({
            type: "POST",
            contentType: "application/json",
            processData: false,
            headers : {
                Authorization: Cookies.get("auth")
            },
            url: "./api/v1/producebatch/"+$('#produceBatchAdd').find('input#produce_id').val()+'/'+$('#produceBatchAdd').find('input#amount').val(),
            success: function( msg ) {
                console.log("Response: " + msg);
                populateProduceBatchAdmin(false);
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to add produce batch!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    /**
     * Recipe Listeners
     */
    $(document).on("click", '.add-recipe', function (e) {
        template = $('#recipeInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template);
        $('#EditModal').html(rendered).promise().done(function () {

        });
        $('#EditModal').modal('open');
        Materialize.updateTextFields();
    });

    $(document).on('click', '.modal-add-recipe', function (e) {
        e.preventDefault();
        $.ajax({
            type: "POST",
            context: $('#EditModal').find('#recipe_name').val(),
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({
                "recipeName": $('#EditModal').find('#recipe_name').val()
            }),
            headers : {
                Authorization: Cookies.get("auth")
            },
            url: "./api/v1/recipe/",
            success: function( msg ) {
                console.log("Response: " + msg);
                populateRecipeAdmin(true);
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update recipe component!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    // Recipe Opening/closing
    $(document).on('click', '.recipe.collapsible-header', function (e) {
        var TargetId = $(this).attr('data-id');
        $.ajax({
            context: TargetId,
            type: "GET",
            contentType: "application/json",
            processData: false,
            crossDomain: true,
            url: "./api/v1/recipecomp/list/"+$(this).attr('data-id'),
            headers : {
                Authorization: Cookies.get("auth")
            },
            success: function( response ) {
                console.log(response);
                var template = $('#RecipeComponentsTemplate').html();
                var data = {};
                data['recipecomponents'] = response;
                data['recipeId'] = TargetId;
                Mustache.parse(template);   // optional, speeds up future uses
                var rendered = Mustache.render(template, data);

                $('div[data-id='+TargetId+']').parent().children('.collapsible-body').html(rendered).promise().done(function () {
                    console.log($('div[data-id='+TargetId+']').parent().children('.collapsible-body'));
                });
            },
            error: function ( msg ) {
                console.log(msg);
                Materialize.toast("Error in loading data!", 4000);
            }
        });
    });

    /**
     * RecipeComponent Listeners
     */
    $(document).on("click", '.add-recipecomp', function (e) {
        template = $('#recipeComponentInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        data = {};
        data["recipeId"] = $(this).attr('data-recipe-id');
        console.log(data);
        var rendered = Mustache.render(template, data);
        $('#EditModal').html(rendered).promise().done(function () {

        });
        $('#EditModal').modal('open');
    });

    $(document).on("click", ".edit-recipecomp", function (e) {
        var recipeId= $(this).attr('data-recipe-id');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            processData: false,
            async: false,
            context: recipeId,
            crossDomain: true,
            url: "./api/v1/recipecomp/"+$(this).attr('data-recipe-id')+'/'+$(this).attr('data-produce-id'),
            headers : {
                Authorization: Cookies.get("auth")
            },
            success: function( response ) {
                data = {};
                data["recipecomponents"] = response;
                data["recipeId"] = recipeId;
                data["produceId"] = response.produceId;
                template = $('#recipeComponentEditTemplate').html();
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

    $(document).on("click", ".delete-recipecomp", function (e) {
        var TargetId = $(this).attr('data-recipe-id');
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            processData: false,
            context: TargetId,
            async: false,
            crossDomain: true,
            url: "./api/v1/recipecomp/"+$(this).attr('data-recipe-id')+'/'+$(this).attr('data-produce-id'),
            headers : {
                Authorization: Cookies.get("auth")
            },
            success: function( response ) {
                console.log(TargetId);
                $.ajax({
                    context: TargetId,
                    type: "GET",
                    contentType: "application/json",
                    processData: false,
                    crossDomain: true,
                    url: "./api/v1/recipecomp/list/"+TargetId,
                    headers : {
                        Authorization: Cookies.get("auth")
                    },
                    success: function( response ) {
                        console.log(response);
                        var template = $('#RecipeComponentsTemplate').html();
                        var data = {};
                        data['recipecomponents'] = response;
                        Mustache.parse(template);   // optional, speeds up future uses
                        var rendered = Mustache.render(template, data);

                        $('div[data-id='+TargetId+']').parent().children('.collapsible-body').html(rendered).promise().done(function () {
                            console.log($('div[data-id='+TargetId+']').parent().children('.collapsible-body'));
                        });
                    },
                    error: function ( msg ) {
                        console.log(msg);
                        Materialize.toast("Error in loading data!", 4000);
                    }
                });
                return response;
            },
            error: function ( msg ) {
                console.log(msg);
                Materialize.toast("Error in deleting recipe component!", 4000);
            }
        });
    });

    $(document).on('click', '.modal-save-recipecomp', function (e) {
        var TargetId = $('#recipeComponentEdit').attr('data-recipe-id');
        e.preventDefault();
        $.ajax({
            type: "PUT",
            context: TargetId,
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({
                "produceId": $('#recipeComponentEdit').attr('data-produce-id'),
                "recipeId": $('#recipeComponentEdit').attr('data-recipe-id'),
                "nomNetto": $('#EditModal').find('#nom_netto').val(),
                "tolerance": $('#EditModal').find('#tolerance').val()
            }),
            headers : {
                Authorization: Cookies.get("auth")
            },
            url: "./api/v1/recipecomp/",
            success: function( msg ) {
                console.log("Response: " + msg);
                console.log(TargetId);
                $.ajax({
                    context: TargetId,
                    type: "GET",
                    contentType: "application/json",
                    processData: false,
                    crossDomain: true,
                    url: "./api/v1/recipecomp/list/"+TargetId,
                    headers : {
                        Authorization: Cookies.get("auth")
                    },
                    success: function( response ) {
                        console.log(response);
                        var template = $('#RecipeComponentsTemplate').html();
                        var data = {};
                        data['recipecomponents'] = response;
                        data['recipeId'] = TargetId;
                        Mustache.parse(template);   // optional, speeds up future uses
                        var rendered = Mustache.render(template, data);

                        $('div[data-id='+TargetId+']').parent().children('.collapsible-body').html(rendered).promise().done(function () {
                            console.log($('div[data-id='+TargetId+']').parent().children('.collapsible-body'));
                        });
                    },
                    error: function ( msg ) {
                        console.log(msg);
                        Materialize.toast("Error in loading data!", 4000);
                    }
                });
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update recipe component!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    $(document).on('click', '.modal-add-recipecomp', function (e) {
        var TargetId = $('#recipeComponentAdd').attr('data-recipe-id');
        e.preventDefault();
        console.log({
            "produceId": $('#EditModal').find('#produce_id').val(),
            "recipeId": $('#recipeComponentAdd').attr('data-recipe-id'),
            "nomNetto": $('#EditModal').find('#nom_netto').val(),
            "tolerance": $('#EditModal').find('#tolerance').val()
        });
        $.ajax({
            type: "POST",
            context: TargetId,
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({
                "produceId": $('#EditModal').find('#produce_id').val(),
                "recipeId": $('#recipeComponentAdd').attr('data-recipe-id'),
                "nomNetto": $('#EditModal').find('#nom_netto').val(),
                "tolerance": $('#EditModal').find('#tolerance').val()
            }),
            headers : {
                Authorization: Cookies.get("auth")
            },
            url: "./api/v1/recipecomp/",
            success: function( msg ) {
                console.log("Response: " + msg);
                console.log(TargetId);
                $.ajax({
                    context: TargetId,
                    type: "GET",
                    contentType: "application/json",
                    processData: false,
                    crossDomain: true,
                    url: "./api/v1/recipecomp/list/"+TargetId,
                    headers : {
                        Authorization: Cookies.get("auth")
                    },
                    success: function( response ) {
                        console.log(response);
                        var template = $('#RecipeComponentsTemplate').html();
                        var data = {};
                        data['recipecomponents'] = response;
                        data['recipeId'] = TargetId;
                        Mustache.parse(template);   // optional, speeds up future uses
                        var rendered = Mustache.render(template, data);

                        $('div[data-id='+TargetId+']').parent().children('.collapsible-body').html(rendered).promise().done(function () {
                            console.log($('div[data-id='+TargetId+']').parent().children('.collapsible-body'));
                        });
                    },
                    error: function ( msg ) {
                        console.log(msg);
                        Materialize.toast("Error in loading data!", 4000);
                    }
                });
                $('#EditModal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update recipe component!", 4000);
                $('#EditModal').modal('close');
            }
        });
    });

    /**
     * General Listeners
     */
    // Login/Logout
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
                $('#Administration').show();
                populateUsersAdmin(false);
                populateProduceAdmin(false);
                //populateProduceBatchAdmin(false);
                //populateProductAdmin(false);
                populateRecipeAdmin(false);
            },
            error: function ( msg ) {
                Materialize.toast("Invalid login!", 4000);
                console.log(msg);
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

    $(document).on('click', '.dropdown-item', function (e) {
        $(this).parent().parent().parent().find('a.dropdown-button').html($(this).text());
    });

    // Tab Switching
    $(document).on('click', 'li.tab', function (e) {
        e.preventDefault();
        switch($(this).children("a").attr("href"))
        {
            case "#UserAdminTab":
                populateUsersAdmin(true);
                break;
            case "#RecipeAdminTab":
                populateRecipeAdmin(true);
                break;
            case "#ProduceAdminTab":
                break;
            case "#ProduceAdminSubTab":
                populateProduceAdmin(true);
                break;
            case "#ProduceBatchAdminSubTab":
                populateProduceBatchAdmin(true);
                break;
            case "#ProductAdminTab":
                populateProductAdmin(true);
                break;
            default:
                break;
        }
    });

    /**
     * Initialization
     */
    if(typeof Cookies.get('auth') !== 'undefined'){
        $('#loginForm').hide();
        $('main').removeClass('valign-wrapper');
        $('#Administration').show();
        populateUsersAdmin(false);
        populateProduceAdmin(false);
        //populateProduceBatchAdmin(false);
        //populateProductAdmin(false);
        populateRecipeAdmin(false);
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

function populateProduceBatchAdmin(notice) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        async: false,
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

            $('#ProduceBatchAdminSubTab>div.left').html(rendered).promise().done(function () {

            });
        },
        error: function ( msg ) {
            console.log(msg);
            Materialize.toast("Error in loading data!", 4000);
        }
    });
    $.ajax({
        type: "GET",
        contentType: "application/json",
        processData: false,
        crossDomain: true,
        url: "./api/v1/producebatch/stock",
        headers : {
            Authorization: Cookies.get("auth")
        },
        success: function( response ) {
            console.log(response);
            var template = $('#ProduceBatchStockAdministrationTemplate').html();
            var data = {};
            data['producebatchstock'] = response;
            Mustache.parse(template);   // optional, speeds up future uses
            var rendered = Mustache.render(template, data);

            $('#ProduceBatchAdminSubTab>div.right').html(rendered).promise().done(function () {

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
    $.ajax({
        type: "GET",
        contentType: "application/json",
        processData: false,
        crossDomain: true,
        url: "./api/v1/product",
        headers : {
            Authorization: Cookies.get("auth")
        },
        success: function( response ) {
            console.log(response);
            var template = $('#ProductBatchAdministrationTemplate').html();
            var data = {};
            data['productbatch'] = response;
            Mustache.parse(template);   // optional, speeds up future uses
            var rendered = Mustache.render(template, data);

            $('#ProductAdminTab').html(rendered).promise().done(function () {
                $('.collapsible').collapsible();
                $('.collapsible-header').each(function () {
                    var TargetId = $(this).attr('data-id');
                })
            });
        },
        error: function ( msg ) {
            console.log(msg);
            if(notice){

            }
        }
    });
}

function populateRecipeAdmin(notice){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        processData: false,
        crossDomain: true,
        url: "./api/v1/recipe",
        headers : {
            Authorization: Cookies.get("auth")
        },
        success: function( response ) {
            console.log(response);
            var template = $('#RecipeAdministrationTemplate').html();
            var data = {};
            data['recipies'] = response;
            Mustache.parse(template);   // optional, speeds up future uses
            var rendered = Mustache.render(template, data);

            $('#RecipeAdminTab').html(rendered).promise().done(function () {
                $('.collapsible').collapsible();
                $('.collapsible-header').each(function () {
                    var TargetId = $(this).attr('data-id');
                    $.ajax({
                        context: TargetId,
                        type: "GET",
                        contentType: "application/json",
                        processData: false,
                        crossDomain: true,
                        url: "./api/v1/recipecomp/list/"+$(this).attr('data-id'),
                        headers : {
                            Authorization: Cookies.get("auth")
                        },
                        success: function( response ) {
                            console.log(response);
                            var template = $('#RecipeComponentsTemplate').html();
                            var data = {};
                            data['recipecomponents'] = response;
                            Mustache.parse(template);   // optional, speeds up future uses
                            var rendered = Mustache.render(template, data);

                            $('div[data-id='+TargetId+']').parent().children('.collapsible-body').html(rendered).promise().done(function () {
                                console.log($('div[data-id='+TargetId+']').parent().children('.collapsible-body'));
                            });
                        },
                        error: function ( msg ) {
                            console.log(msg);
                        }
                    });
                })
            });
        },
        error: function ( msg ) {
            console.log(msg);
            if(notice){

            }
        }
    });
}