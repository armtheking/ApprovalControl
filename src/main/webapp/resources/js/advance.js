
function addRowBudget_adv() {

    var rowCount = $('#tbbudget tr').length;

    num_index = rowCount - 1;
    num = rowCount;

        if ($('#tbbudget tr').length <= 10) {
            var n = "<tr>";
            n = n + "<td>" + num + "</td>";
            n = n + "<td><form:select path=\"ticketdetail[0].financeChargeCode.id\" items=\"${chargeCode}\" itemValue=\"id\" itemLabel=\"description\" class=\"form-control input-sm\"/>";
            n = n +   "<div class=\"has-error\">";
            n = n + "<form:errors path=\"ticketdetail[0].financeChargeCode.id\" class=\"help-inline\"/>  </div></td>";                     
            n = n + "<td> <textarea id=\"budgetDetail" + num_index + "\" class=\"form-control\"  name=\"ticketHeader.ticketDTraining[" + num_index + "].budgetDetail\" style=\" resize: none;]\">  </textarea></td>";
            n = n + "<td><input type=\"text\" id=\"amount" + num_index + "\"name=\"ticketHeader.ticketDTraining[" + num_index + "].amount\" class=\"form-control\"   value=\"0\" /></td>";
            n = n + "</tr>";
            $('#tbbudget > tbody:last').append(n);

        }


}

function deleteRowBudget_adv() {
    if ($('#tbbudget tr').length != 2) {
        num--;
        num_index--;
        $('#tbbudget > tbody tr:last, this').remove();
    }
}

