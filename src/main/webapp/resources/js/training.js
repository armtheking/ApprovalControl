
var count = 0;
var count_line = 1;

var count_budget = 0;
var count_budget_line = 1;


var num = 5;
var num_index = 4;


var i;
var check = true;
var check_budget = true;
//var check_type = null;
//var check_plan = null;
//var check_payment = null;


$(document).ready(function () {
 
    $('#example').DataTable();
    $('#example2').DataTable();
    $(function () {

        $('#startDate').datetimepicker();

        $('#endDate').datetimepicker();

        $('#paymentDate').datetimepicker();
//        $('#endDate').datepicker({
//            dateFormat: 'dd/mm/yy'
//        });
//        $('#paymentDate').datepicker({
//            dateFormat: 'dd/mm/yy'
//        });
    });
});


$(document).ready(function () {
    $('#all').DataTable();
    $('#thaniya').DataTable();
    $('#blc').DataTable();
    $('#nlc').DataTable();
    $('#airport').DataTable();
    $('#ncc').DataTable();
    $('#laemchabang').DataTable();
    $('#korat').DataTable();
});


function addRowParticipant() {

    var rowCount = $('#myTbl tr').length;
    count_line = rowCount + 1;
    count = rowCount;
    for (i = 0; i <= count; i++)
    {
        var emp = $('#empid' + i).val();
        var first = $('#firstname' + i).val();
        var last = $('#lastname' + i).val();
        var sec = $('#section' + i).val();
        if (emp == "" || first == "" || last == "" || sec == "") {
            check = false;
        }

    }
    if (check == true) {
        var tr = "<tr>";
        tr = tr + "<td>" + count_line++ + ".&nbsp;</td>";
        tr = tr + "<td style=\'width:200px\'><input type=\'text\'  id=\'empid" + count + "\' name=\'ticketHeader.trainingParticipant[" + count + "].empID\' class=\'form-control\' placeholder=\'EmployeeID\' required/></td>";
        tr = tr + "<td style=\'width:200px\'><input type=\'text\'  id=\'firstname" + count + "\' name=\'ticketHeader.trainingParticipant[" + count + "].firstName\' class=\'form-control\' placeholder=\'Firstname\' required/></td>";
        tr = tr + "<td style=\'width:200px\'><input type=\'text\'  id=\'lastname" + count + "\' name=\'ticketHeader.trainingParticipant[" + count + "].lastName\' class=\'form-control\' placeholder=\'Lastname\' required/></td>";
        tr = tr + "<td style=\'width:200px\'><input type=\'text\'  id=\'section" + count + "\' name=\'ticketHeader.trainingParticipant[" + count + "].section\' class=\'form-control\' placeholder=\'Section\' required/></td>";
        tr = tr + "</tr>";

        $('#myTbl > tbody:last').append(tr);
    }
    else {
        check = true;
        alert('Not Null Participant');
    }

}
function deleteRowParticipant() {
    if ($('#myTbl tr').length != 1) {
        count--;
        count_line--;
        $('#myTbl > tbody tr:last, this').remove();
    }
}

function addRowBudget() {

    var rowCount = $('#tbbudget tr').length;

    num_index = rowCount - 1;
    num = rowCount;
    for (i = 0; i <= num_index; i++)
    {
        var item = $('#item' + i).val();
        var amount = $('#amount' + i).val();
        if (item == "" || amount == "") {
            check = false;
        }

    }

    if (check == true) {

        if ($('#tbbudget tr').length <= 10) {
            var n = "<tr>";
            n = n + "<td>" + num + "</td>";
            n = n + "<td><input type=\"text\" id=\"item" + num_index + "\"name=\"ticketHeader.ticketDTraining[" + num_index + "].item\" class=\"form-control\"  /></td>";
            n = n + "<td> <textarea id=\"budgetDetail" + num_index + "\" class=\"form-control\"  name=\"ticketHeader.ticketDTraining[" + num_index + "].budgetDetail\" style=\" resize: none;]\">  </textarea></td>";
            n = n + "<td><input type=\"text\" id=\"amount" + num_index + "\"name=\"ticketHeader.ticketDTraining[" + num_index + "].amount\" class=\"form-control\"   value=\"0\" /></td>";


            n = n + "</tr>";
            $('#tbbudget > tbody:last').append(n);

        }
    }
    else {
        check = true;
        alert('Not Null Budget');
    }


}

function deleteRowBudget() {
    if ($('#tbbudget tr').length != 6) {
        num--;
        num_index--;
        $('#tbbudget > tbody tr:last, this').remove();
    }

}
