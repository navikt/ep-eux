package no.nav.eessi.pensjon.eux.model.buc

/**
 * helper class to check if bucid exist any longer in rina.
 * (lost in migrate to rina2020)
 */
object MissingBuc {

    fun checkForMissingBuc(euxCaseId: String) = euxCaseId in missingBuclist

    //Teams documents - "Savnete saker i RINA2020.xls" - ikke migrert
    private val missingBuclist = listOf(
        "10002113",
        "6006777",
        "100885",
        "1035386",
        "105123",
        "106127",
        "1069125",
        "1105109",
        "111864",
        "112466",
        "1131875",
        "1138620",
        "1141164",
        "1147496",
        "1152280",
        "120419",
        "1205402",
        "1219765",
        "1241978",
        "1282333",
        "1295024",
        "1297263",
        "1314610",
        "1337660",
        "1346842",
        "137061",
        "1375852",
        "1382223",
        "1405576",
        "142003",
        "1490563",
        "150161",
        "1510326",
        "1525932",
        "1535837",
        "1543381",
        "1544911",
        "160458",
        "1626002",
        "1715069",
        "1773276",
        "1863665",
        "1866307",
        "1905730",
        "190627",
        "1923946",
        "1947383",
        "196182",
        "204267",
        "206889",
        "2186623",
        "220073",
        "22339",
        "239510",
        "240816",
        "241663",
        "243236",
        "2437178",
        "249638",
        "251774",
        "263473",
        "269967",
        "280229",
        "282554",
        "291077",
        "293897",
        "309248",
        "3129463",
        "3298983",
        "3308477",
        "3501816",
        "3633887",
        "3795607",
        "3817053",
        "3817013",
        "3837987",
        "3880091",
        "4135673",
        "4194555",
        "4338236",
        "482417",
        "513900",
        "5140799",
        "5181974",
        "5418228",
        "5462073",
        "5476702",
        "555685",
        "5614593",
        "572756",
        "5760383",
        "5815950",
        "5822948",
        "5827608",
        "5840516",
        "5984770",
        "6027808",
        "609470",
        "628905",
        "6313014",
        "6415488",
        "643713",
        "6460857",
        "6569308",
        "6613766",
        "6625994",
        "6682130",
        "6702991",
        "6703276",
        "6703309",
        "6703323",
        "6721406",
        "675623",
        "6762743",
        "6762755",
        "6762767",
        "6791814",
        "683606",
        "6851769",
        "693610",
        "6957689",
        "7051260",
        "7056993",
        "705930",
        "7217206",
        "7304420",
        "7345275",
        "7373146",
        "737490",
        "7430868",
        "743400",
        "7475113",
        "7479134",
        "7522631",
        "7584226",
        "7584672",
        "7612074",
        "765311",
        "775603",
        "779867",
        "782547",
        "783837",
        "7857742",
        "786053",
        "7945458",
        "799857",
        "801131",
        "814367",
        "8356301",
        "838424",
        "862708",
        "867619",
        "86805",
        "870273",
        "872271",
        "872804",
        "874588",
        "882526",
        "88777",
        "8906400",
        "8906364",
        "8907190",
        "8907148",
        "8907202",
        "8907235",
        "8932215",
        "893626",
        "8971889",
        "8993517",
        "9025527",
        "9037574",
        "924448",
        "92909",
        "9364403",
        "940845",
        "950168",
        "9525622",
        "9535169",
        "958644",
        "9588544",
        "9588453",
        "9588602",
        "9588352",
        "9612042",
        "9621005",
        "9636551",
        "9640497",
        "9640263",
        "9640423",
        "9640540",
        "9640611",
        "9640806",
        "9640707",
        "9640878",
        "9857012",
        "9859551",
        "9859563",
        "9859655",
        "9859667",
        "9900188",
        "1705795",
        "9927256",
        "10497992",
        "10498046",
        "10498062",
        "10490811",
        "10498497",
        "10498528",
        "7973109",
        "10029792",
        "10516902",
        "10304117",
        "10577594",
        "5686740",
        "9043182",
        "10180178",
        "1390909" // 2024-02-29: eldre buc som ikke lenger er tilgjengelig i rina
    )

}
