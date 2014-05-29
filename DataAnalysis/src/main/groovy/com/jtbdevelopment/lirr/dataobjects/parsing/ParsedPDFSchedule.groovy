package com.jtbdevelopment.lirr.dataobjects.parsing
/**
 * Date: 2/15/14
 * Time: 2:50 PM
 */
class ParsedPDFSchedule {
    String title
    String modified
    String subject

    List<String> eastbound1 = []
    List<String> eastbound2 = []
    List<String> westbound1 = []
    List<String> westbound2 = []
}
