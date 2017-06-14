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

    $(".button-collapse").sideNav();

    $('#MainTabs').tabs();
    $('#ProduceSubTabs').tabs();

    /**
     * ProductBatch Listeners
     */
    // ProductBatch Opening/closing
    $(document).on('click', ".product.collapsible-header", function (e) {
        if(!$(this).hasClass('active')){return;} //Only continue if accordion isn't active
        var clicked_element_id = $(this).attr('data-id');
        doAjax(
            "GET",
            "./api/v1/productbatchcomp/list/"+$(this).attr('data-id'),
            "",
            true,
            $('#productBatchComponentsTemplate').html(),
            $('div[data-id='+$(this).attr('data-id')+']').parent().children('.collapsible-body'),
            function( response ) {
                var clicked_element = $('.product.collapsible-header[data-id='+clicked_element_id+']');
                clicked_element.parent().find('a.add-productbatchcomp').attr('data-productbatch-id', clicked_element.attr('data-id'));
            }
        );
    });

    $(document).on("click", '.add-recipecomp', function (e) {
        template = $('#recipeComponentInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        data = {};
        data["recipeId"] = $(this).attr('data-recipe-id');
        var rendered = Mustache.render(template, data);
        $('#EditModal').html(rendered).promise().done(function () {
            $('#EditModal').modal('open');
            Materialize.updateTextFields();
        });
    });

    $(document).on("click", '.add-productbatch', function (e) {
        template = $('#productBatchInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template);
        $('#EditModal').html(rendered).promise().done(function () {
            $('#EditModal').modal('open');
            Materialize.updateTextFields();
        });
    });

    $(document).on('click', '.modal-add-productbatch', function (e) {
        e.preventDefault();
        doAjax(
            "POST",
            "./api/v1/productbatch/",
            {
                pbId: $('#productBatchAdd').find('#productbatch_id').val(),
                recipeId: $('#productBatchAdd').find('#recipe_id').val()
            },
            true,
            $('#userEditTemplate').html(),
            $('#EditModal'),
            function( response ) {
                populateProductAdmin(false);
                $('#EditModal').modal('close');
            }
        );
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
        var oprId = $(this).attr('data-id');
        doAjax(
            "GET",
            "./api/v1/operator/"+oprId,
            "",
            true,
            $('#userEditTemplate').html(),
            $('#EditModal'),
            function( response ) {
                var select_box = $('#EditModal').find('#user_role');
                var option = 'option[value='+select_box.attr('data-selected')+']';
                select_box.find(option).attr("selected", true);
                $('select').material_select();
                $('#EditModal').modal('open');
            }
        );
    });

    $(document).on("click", ".edit-profile", function (e) {
        doAjax(
            "GET",
            "./api/v1/operator/me",
            "",
            true,
            $('#userEditProfileTemplate').html(),
            $('#EditModal'),
            function( response ) {
                var select_box = $('#EditModal').find('#user_role');
                var option = 'option[value='+select_box.attr('data-selected')+']';
                select_box.find(option).attr("selected", true);
                $('select').material_select();
                $('#EditModal').modal('open');
            }
        );
    });

    $(document).on("click", '.delete-user', function (e) {
        var oprId = $(this).attr('data-id');
        doAjax(
            "DELETE",
            "./api/v1/operator/"+oprId,
            "",
            true,
            null,
            null,
            function( response ) {
                Materialize.toast("User with ID: "+ oprId +" was deleted!", 4000);
                populateUsersAdmin(false);
            }
        );
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
        doAjax(
            "POST",
            "./api/v1/operator/",
            {
                "oprId": $('#userAdd').find("#user_id").val(),
                "oprName": $('#userAdd').find("#user_name").val(),
                "ini": $('#userAdd').find("#user_ini").val(),
                "cpr": $('#userAdd').find("#user_cpr").val(),
                "admin": $('#userAdd').find("#user_admin").is(':checked'),
                "role": role
            },
            true,
            null,
            null,
            function( response ) {
                populateUsersAdmin(false);
                $('#EditModal').modal('close');
            }
        );
    });

    $(document).on('click', '.modal-save-user', function (e) {
        e.preventDefault();
        var role;
        if($('#userEdit').find("#user_role").find('li.active.selected').text() === ''){
            role = $('#userEdit').find('input.select-dropdown').attr('value');
        }else{
            role = $('#userEdit').find("#user_role").find('li.active.selected').text()
        }
        doAjax(
            "PUT",
            "./api/v1/operator/",
            {
                "oprId": $('#userEdit').find("#user_id").val(),
                "oprName": $('#userEdit').find("#user_name").val(),
                "ini": $('#userEdit').find("#user_ini").val(),
                "cpr": $('#userEdit').find("#user_cpr").val(),
                "password": $('#userEdit').find("#user_password").val(),
                "admin": $('#userEdit').find("#user_admin").is(':checked'),
                "role": role
            },
            true,
            $('#produceEditTemplate').html(),
            $('#EditModal'),
            function( response ) {
                populateUsersAdmin();
                $('#EditModal').modal('close');
            }
        );
    });

    $(document).on('click', '.modal-save-user-profile', function (e) {
        e.preventDefault();
        var role;
        if($('#userEditProfile').find("#user_role").find('li.active.selected').text() === ''){
            role = $('#userEditProfile').find('input.select-dropdown').attr('value');
        }else{
            role = $('#userEditProfile').find("#user_role").find('li.active.selected').text()
        }
        doAjax(
            "PUT",
            "./api/v1/operator/update",
            {
                "oprId": $('#userEditProfile').find("#user_id").val(),
                "oprName": $('#userEditProfile').find("#user_name").val(),
                "ini": $('#userEditProfile').find("#user_ini").val(),
                "cpr": $('#userEditProfile').find("#user_cpr").val(),
                "password": $('#userEditProfile').find("#user_password").val(),
                "newPassword": $('#userEditProfile').find("#user_new_password").val(),
                "admin": $('#userEditProfile').find("#user_admin").is(':checked'),
                "role": role
            },
            true,
            $('#produceEditTemplate').html(),
            $('#EditModal'),
            function( response ) {
                populateUsersAdmin();
                $('#EditModal').modal('close');
            }
        );
    });

    /**
     * Produce Listeners
     */
    $(document).on("click", '.add-produce', function (e) {
        template = $('#produceInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template);
        $('#EditModal').html(rendered).promise().done(function () {
            $('#EditModal').modal('open');
        });
        Materialize.updateTextFields();
    });

    $(document).on("click", ".edit-produce", function (e) {
        var produce_id = $(this).attr('data-id');
        doAjax(
            "GET",
            "./api/v1/produce/"+produce_id,
            "",
            true,
            $('#produceEditTemplate').html(),
            $('#EditModal'),
            function( response ) {
                $('#EditModal').modal('open');
            }
        );
    });

    $(document).on("click", '.delete-produce', function (e) {
        doAjax(
            "DELETE",
            "./api/v1/produce/"+$(this).attr('data-id'),
            "",
            true,
            null,
            null,
            function( response ) {
                populateProduceAdmin(false);
            }
        );
    });

    $(document).on('click', '.modal-save-produce', function (e) {
        e.preventDefault();
        doAjax(
            "PUT",
            "./api/v1/produce/",
            {
                produceId: $('#produceEdit').attr('data-source-id'),
                produceName: $('#produceEdit').find('input#produce_name').val(),
                supplier: $('#produceEdit').find('input#produce_supplier').val()
            },
            true,
            null,
            null,
            function( response ) {
                populateProduceAdmin(false);
                $('#EditModal').modal('close');
            }
        );
    });

    $(document).on('click', '.modal-add-produce', function (e) {
        e.preventDefault();
        doAjax(
            "POST",
            "./api/v1/produce/",
            {
                produceId: $('#produceAdd').find('input#produce_id').val(),
                produceName: $('#produceAdd').find('input#produce_name').val(),
                supplier: $('#produceAdd').find('input#produce_supplier').val()
            },
            true,
            null,
            null,
            function( response ) {
                populateProduceAdmin(false);
                $('#EditModal').modal('close');
            }
        );
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
        doAjax(
            "GET",
            "./api/v1/producebatch/"+$(this).attr('data-id'),
            "",
            true,
            $('#produceBatchEditTemplate').html(),
            $('#EditModal'),
            function( response ) {
                $('#EditModal').modal('open');
            }
        );
    });

    $(document).on("click", '.delete-producebatch', function (e) {
        doAjax(
            "DELETE",
            "./api/v1/producebatch/"+$(this).attr('data-id'),
            "",
            true,
            null,
            null,
            function( response ) {
                populateProduceBatchAdmin(false);
            }
        );
    });

    $(document).on('click', '.modal-save-producebatch', function (e) {
        e.preventDefault();
        doAjax(
            "PUT",
            "./api/v1/producebatch/",
            {
                rbId: $('#produceBatchEdit').attr('data-source-id'),
                produceName: $('#produceBatchEdit').find('input#produceName').val(),
                supplier: $('#produceBatchEdit').find('input#supplier').val(),
                produceId: $('#produceBatchEdit').find('input#produceId').val(),
                amount: $('#produceBatchEdit').find('input#amount').val()
            },
            true,
            null,
            null,
            function( response ) {
                populateProduceBatchAdmin(false);
                $('#EditModal').modal('close');
            }
        );
    });

    $(document).on('click', '.modal-add-producebatch', function (e) {
        e.preventDefault();
        doAjax(
            "POST",
            "./api/v1/producebatch/",
            {
                rbId: $('#EditModal').find('#producebatch_id').val(),
                produceId: $('#EditModal').find('#produce_id').val(),
                amount: $('#EditModal').find('#amount').val(),
                produceName: "",
                supplier: ""
            },
            true,
            null,
            null,
            function( response ) {
                populateProduceBatchAdmin(false)
                $('#EditModal').modal('close');
            }
        );
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
        doAjax(
            "POST",
            "./api/v1/recipe/",
            {
                recipeId: $('#EditModal').find('#recipe_id').val(),
                recipeName: $('#EditModal').find('#recipe_name').val()
            },
            true,
            null,
            null,
            function( response ) {
                populateRecipeAdmin(false);
                $('#EditModal').modal('close');
            }
        );
    });

    // Recipe Opening/closing
    $(document).on('click', '.recipe.collapsible-header', function (e) {
        if(!$(this).hasClass('active')){return;} //Only continue if accordion isn't active
        var clicked_element_id = $(this).attr('data-id');
        doAjax(
            method = "GET",
            url = "./api/v1/recipecomp/list/"+$(this).attr('data-id'),
            data = "",
            notice= true,
            template = $('#RecipeComponentsTemplate').html(),
            dom_target = $('div[data-id='+$(this).attr('data-id')+']').parent().children('.collapsible-body'),
            callback = function( response ) {
                var clicked_element = $('.recipe.collapsible-header[data-id='+clicked_element_id+']');
                clicked_element.parent().find('a.add-recipecomp').attr('data-recipe-id', clicked_element.attr('data-id'));
            }
        );
    });

    /**
     * RecipeComponent Listeners
     */
    $(document).on("click", '.add-recipecomp', function (e) {
        template = $('#recipeComponentInsertTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        data = {};
        data["recipeId"] = $(this).attr('data-recipe-id');
        var rendered = Mustache.render(template, data);
        $('#EditModal').html(rendered).promise().done(function () {
            $('#EditModal').modal('open');
        });
    });

    $(document).on("click", ".edit-recipecomp", function (e) {
        var recipe_id = $(this).attr('data-recipe-id');
        var produce_id = $(this).attr('data-produce-id');
        doAjax(
            "GET",
            "./api/v1/recipecomp/"+recipe_id+'/'+produce_id,
            "",
            true,
            $('#recipeComponentEditTemplate').html(),
            $('#EditModal'),
            function( response ) {
                $('#recipeComponentEdit').attr('data-recipe-id', recipe_id).attr('data-produce-id', produce_id)
                $('#EditModal').modal('open');
            }
        );
    });

    $(document).on("click", ".delete-recipecomp", function (e) {
        var recipe_id = $(this).attr('data-recipe-id');
        var produce_id = $(this).attr('data-produce-id')
        doAjax(
            "DELETE",
            "./api/v1/recipecomp/"+recipe_id+'/'+produce_id,
            "",
            true,
            null,
            null,
            function( response ) {
                doAjax(
                    "GET",
                    "./api/v1/recipecomp/list/"+recipe_id,
                    "",
                    true,
                    $('#RecipeComponentsTemplate').html(),
                    $('div.recipe[data-id='+recipe_id+']').parent().children('.collapsible-body'),
                    function( response ) {

                    }
                );
            }
        );
    });

    $(document).on('click', '.modal-save-recipecomp', function (e) {
        var recipe_id = $('#recipeComponentEdit').attr('data-recipe-id');
        doAjax(
            "PUT",
            "./api/v1/recipecomp/",
            {
                "produceId": $('#recipeComponentEdit').attr('data-produce-id'),
                "recipeId": $('#recipeComponentEdit').attr('data-recipe-id'),
                "nomNetto": $('#EditModal').find('#nom_netto').val(),
                "tolerance": $('#EditModal').find('#tolerance').val()
            },
            true,
            null,
            null,
            function( response ) {
                doAjax(
                    "GET",
                    "./api/v1/recipecomp/list/"+recipe_id,
                    "",
                    true,
                    $('#RecipeComponentsTemplate').html(),
                    $('div.recipe[data-id='+recipe_id+']').parent().children('.collapsible-body'),
                    function( response ) {
                        $('#EditModal').modal('close');
                    }
                );
            }
        );
    });

    $(document).on('click', '.modal-add-recipecomp', function (e) {
        var recipe_id = $('#recipeComponentAdd').attr('data-recipe-id');
        doAjax(
            "POST",
            "./api/v1/recipecomp/",
            {
                "produceId": $('#EditModal').find('#produce_id').val(),
                "recipeId": recipe_id,
                "nomNetto": $('#EditModal').find('#nom_netto').val(),
                "tolerance": $('#EditModal').find('#tolerance').val()
            },
            true,
            null,
            null,
            function( response ) {
                doAjax(
                    "GET",
                    "./api/v1/recipecomp/list/"+recipe_id,
                    "",
                    true,
                    $('#RecipeComponentsTemplate').html(),
                    $('div.recipe[data-id='+recipe_id+']').parent().children('.collapsible-body'),
                    function( response ) {
                        $('div.recipe[data-id='+recipe_id+']').parent().find('.add-recipecomp').attr('data-recipe-id', recipe_id);
                        $('#EditModal').modal('close');
                    }
                );
            }
        );
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
                Cookies.set("auth", msg, { expires : 7 });
                $('#loginForm').hide();
                $('main').removeClass('valign-wrapper');
                $("nav ul.right").show();
                $('#Administration').show();
                populateUsersAdmin(false);
                populateProduceAdmin(false);
                //populateProduceBatchAdmin(false);
                //populateProductAdmin(false);
                populateRecipeAdmin(false);
            },
            error: function ( msg ) {
                Materialize.toast("Invalid login!", 4000);
            }
        });
    });

    $('.logout').on('click', function (e) {
        e.preventDefault();
        $('#Administration').hide();
        $("nav ul.right").hide();
        $('#loginForm').show();
        $('main').addClass('valign-wrapper');
        Cookies.remove('auth');
        window.location.replace("./");
    });

    $(document).on('click', '.dropdown-item', function (e) {
        $(this).parent().parent().parent().find('a.dropdown-button').html($(this).text());
    });

    // Tab Switching
    $(document).on('click', 'li.tab:not(.disabled)', function (e) {
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
                populateProduceAdmin(true);
                populateProduceBatchAdmin(true);
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

    $(document).on('click', '#logo', function (e) {
        $('#hiddenModal').modal('open');
    });

    $(document).on('click','#resetData', function () {
        doAjax(
            "GET",
            "./api/v1/database/reset",
            "",
            false,
            null,
            null,
            function (response) {
                Materialize.toast(response, 4000);
                $('#hiddenModal').modal('close');
                populateUsersAdmin(false);
                populateProduceAdmin(false);
                populateProduceBatchAdmin(false);
                populateProductAdmin(false);
                populateRecipeAdmin(false);
            }
        );
    });

    /**
     * Initialization
     */
    if(typeof Cookies.get('auth') !== 'undefined'){
        $('#loginForm').hide();
        $('main').removeClass('valign-wrapper');
        $('#Administration').show();
        $("nav ul.right").show();
        populateUsersAdmin(false);
        populateProduceAdmin(false);
        populateProduceBatchAdmin(false);
        populateProductAdmin(false);
        populateRecipeAdmin(false);
    }
});

function populateUsersAdmin(notice) {
    doAjax(
        "GET",
        "./api/v1/operator/",
        "",
        notice,
        $('#UserAdministrationTemplate').html(),
        $('#UserAdminTab'),
        function( response ) {
            $("input:checkbox:not(:checked)").each(function () {
                $(this).prop("indeterminate", true);
            });
        },
        $("a[href='#UserAdminTab']")
    );
}

function populateProduceAdmin(notice) {
    doAjax(
        "GET",
        "./api/v1/produce/",
        "",
        true,
        $('#ProduceAdministrationTemplate').html(),
        $('#ProduceAdminSubTab'),
        function (response) {
            if($("a[href='#ProduceAdminSubTab']").parent().hasClass('disabled') && $("a[href='#ProduceBatchAdminSubTab']").parent().hasClass('disabled')){
                $("a[href='#ProduceAdminTab']").parent().addClass('disabled');
            }
        },
        $("a[href='#ProduceAdminSubTab']")
    );
}

function populateProduceBatchAdmin(notice) {
    doAjax(
        "GET",
        "./api/v1/producebatch/",
        "",
        notice,
        $('#ProduceBatchAdministrationTemplate').html(),
        $('#ProduceBatchAdminSubTab>div.left'),
        function (response) {
            if($("a[href='#ProduceAdminSubTab']").parent().hasClass('disabled') && $("a[href='#ProduceBatchAdminSubTab']").parent().hasClass('disabled')){
                $("a[href='#ProduceAdminTab']").parent().addClass('disabled');
            }
        },
        $("a[href='#ProduceBatchAdminSubTab']")
    );
    doAjax(
        "GET",
        "./api/v1/producebatch/stock",
        "",
        notice,
        $('#ProduceBatchStockAdministrationTemplate').html(),
        $('#ProduceBatchAdminSubTab>div.right'),
        function (response) {
            if($("a[href='#ProduceAdminSubTab']").parent().hasClass('disabled') && $("a[href='#ProduceBatchAdminSubTab']").parent().hasClass('disabled')){
                $("a[href='#ProduceAdminTab']").parent().addClass('disabled');
            }
        },
        $("a[href='#ProduceBatchAdminSubTab']")
    );
}

function populateProductAdmin(notice) {
    doAjax(
        "GET",
        "./api/v1/productbatch",
        "",
        notice,
        $('#ProductBatchAdministrationTemplate').html(),
        $('#ProductAdminTab'),
        function (response) {
            $('.collapsible').collapsible();
            $('.product.collapsible-header').each(function () {
                doAjax(
                    "GET",
                    "./api/v1/productbatchcomp/list/"+$(this).attr('data-id'),
                    "",
                    notice,
                    $('#productBatchComponentsTemplate').html(),
                    $('div.product[data-id='+$(this).attr('data-id')+']').parent().children('.collapsible-body'),
                    function( response ) {

                    }
                );
            })
        },
        $("a[href='#ProductAdminTab']")
    );
}

function populateRecipeAdmin(notice){
    doAjax(
        "GET",
        "./api/v1/recipe",
        "",
        notice,
        $('#RecipeAdministrationTemplate').html(),
        $('#RecipeAdminTab'),
        function (response) {
            $('.collapsible').collapsible();
            $('.recipe.collapsible-header').each(function () {
                var recipe_id = $(this).attr('data-id');
                doAjax(
                    "GET",
                    "./api/v1/recipecomp/list/"+recipe_id,
                    "",
                    false,
                    $('#RecipeComponentsTemplate').html(),
                    $('div.recipe[data-id='+recipe_id+']').parent().children('.collapsible-body'),
                    function( response ) {

                    }
                );
            })
        },
        $("a[href='#RecipeAdminTab']")
    );
}

function doAjax(method, url, data, notice, template, dom_target, callback, contextTab){
    var contentType = "application/json";
    if(typeof data !== "string"){
        data = JSON.stringify(data);
    }
    $.ajax({
        type: method,
        context: contextTab,
        contentType: contentType,
        processData: false,
        crossDomain: true,
        url: url,
        data: data,
        headers : {
            Authorization: Cookies.get("auth")
        },
        statusCode:{
            401 : function (response) {
                callback(response);
            },
            403 : function (response) {
                callback(response);
            }
        },
        success: function(response) {
            if(template !== null){
                Mustache.parse(template);   // optional, speeds up future uses
                var rendered = Mustache.render(template, response);
                dom_target.html(rendered).promise().done(function () {
                    callback(response);
                });
            }else{
                callback(response);
            }
        },
        error: function ( msg ) {
            ajaxErrorHandler(msg, notice, contextTab);
        }
    })
}

function ajaxErrorHandler(msg, notice, contextTab) {
    if(notice){
        switch(msg.status) {
            case 500:
                Materialize.toast("An unexpected error ocurred!", 4000);
                break;
            case 404:
                break;
            case 401:
            case 403:
                if(contextTab !== "undefined"){
                    contextTab.parent().addClass('disabled');
                    $('#MainTabs').tabs();
                    $('#ProduceSubTabs').tabs();
                }
                Materialize.toast("Unauthorized!", 4000);
                break;
            default:
                Materialize.toast(msg.responseText, 4000);
                break;
        }
    }
    else{
        switch(msg.status) {
            case 500:
                break;
            case 404:
                break;
            case 401:
            case 403:
                if(contextTab !== "undefined"){
                    contextTab.parent().addClass('disabled');
                    $('#MainTabs').tabs();
                    $('#ProduceSubTabs').tabs();
                }
                break;
            default:
                break;
        }
    }
}