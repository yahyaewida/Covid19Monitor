package iti.mobilenative.covid19monitoring.model.pojo

data class Case(
    var title: String,
    var numbers: String,
    var color: Int
){
    constructor(): this("",
        "",
        0)
}