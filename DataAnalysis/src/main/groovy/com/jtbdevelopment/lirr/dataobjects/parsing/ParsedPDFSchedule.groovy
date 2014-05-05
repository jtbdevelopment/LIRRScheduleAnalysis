package com.jtbdevelopment.LIRR.dataobjects.parsing
/**
 * Date: 2/15/14
 * Time: 2:50 PM
 */
class ParsedPDFSchedule {
    String title
    String modified
    String subject

    List<String> eastboundWeekdays = []
    List<String> eastboundWeekends = []
    List<String> westboundWeekdays = []
    List<String> westboundWeekends = []
}
