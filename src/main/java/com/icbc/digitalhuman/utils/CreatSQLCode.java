package com.icbc.digitalhuman.Utils;

import com.icbc.digitalhuman.Entity.NecessaryInfo;
import com.icbc.digitalhuman.Entity.UnnecessaryInfo;

public class CreatSQLCode {
    public static String WriteSQLCode(NecessaryInfo necessaryInfo, UnnecessaryInfo unnecessaryInfo){
        System.out.println("INSERT INTO business_information_for_approval(interface_input_parameters," +
                "is_retrys_supported,"+
                "is_interrupt_possible,"+
                "estimated_timeIn_minutes,"+
                "application_type,"+
                "effective_date,"+
                "delivery_date,"+
                "production_date,"+
                "version,"+
                "project_name,"+
                "requirement_sub_item,"+
                "application,"+
                "batch_category,"+
                "batch_session,"+
                "job_description,"+
                "prerequisite_job,"+
                "execution_frequency,"+
                "execution_scope,"+

                "stored_procedure_interface,"+
                "input_parameter_description,"+
                "output_parameters,"+
                "interruption_solution,"+
                "upstream_application,"+
                "file_interface_name,"+
                "is_file_structure_changed,"+
                "job_name,"+
                "execution_frequency_description,"+
                "upstream_application_chinese_name,"+
                "is_temporary_table_fields_required,"+
                "hasLegacy_files,"+
                "upstream_text_description,"+
                "upstream_contact,"+
                "upstream_file_fransfer_method,"+
                "upstream_point_to_point_transmission,"+
                "downstream_application,"+
                "downstream_target_interface,"+
                "downstream_contact,"+
                "downstream_point_to_point_transmission,"+
                "downstream_file_transfer_method,"+
                "development_team_description,"+
                "applicant,"+
                "application_time,"+
                "execution_scope_description,"+
                ")"+"VALUES("+
                necessaryInfo.interfaceInputParameters+"\' , \'"+
                necessaryInfo.isRetrySupported+"\' , \'"+
                necessaryInfo.isInterruptPossible+"\' , \'"+
                necessaryInfo.estimatedTimeInMinutes+"\' , \'"+
                necessaryInfo.applicationType+"\' , \'"+
                necessaryInfo.effectiveDate+"\' , \'"+
                necessaryInfo.deliveryDate+"\' , \'"+
                necessaryInfo.productionDate+"\' , \'"+
                necessaryInfo.version+"\' , \'"+
                necessaryInfo.centralProjectNumber+"\' , \'"+
                necessaryInfo.projectName+"\' , \'"+
                necessaryInfo.requirementSubItem+"\' , \'"+
                necessaryInfo.application+"\' , \'"+
                necessaryInfo.batchCategory+"\' , \'"+
                necessaryInfo.batchSession+"\' , \'"+
                necessaryInfo.jobDescription+"\' , \'"+
                necessaryInfo.prerequisiteJob+"\' , \'"+
                necessaryInfo.executionFrequency+"\' , \'"+
                necessaryInfo.executionScope+"\' , \'"+

                unnecessaryInfo.storedProcedureInterface+"\' , \'"+
                unnecessaryInfo.inputParameterDescription+"\' , \'"+
                unnecessaryInfo.outputParameters+"\' , \'"+
                unnecessaryInfo.interruptionSolution+"\' , \'"+
                unnecessaryInfo.upstreamApplication+"\' , \'"+
                unnecessaryInfo.fileInterfaceName+"\' , \'"+
                unnecessaryInfo.isFileStructureChanged+"\' , \'"+
                unnecessaryInfo.jobName+"\' , \'"+
                unnecessaryInfo.executionFrequencyDescription+"\' , \'"+
                unnecessaryInfo.upstreamApplicationChineseName+"\' , \'"+
                unnecessaryInfo.isTemporaryTableFieldsRequired+"\' , \'"+
                unnecessaryInfo.hasLegacyFiles+"\' , \'"+
                unnecessaryInfo.upstreamTextDescription+"\' , \'"+
                unnecessaryInfo.upstreamContact+"\' , \'"+
                unnecessaryInfo.upstreamFileTransferMethod+"\' , \'"+
                unnecessaryInfo.upstreamPointToPointTransmission+"\' , \'"+
                unnecessaryInfo.downstreamApplication+"\' , \'"+
                unnecessaryInfo.downstreamTargetInterface+"\' , \'"+
                unnecessaryInfo.downstreamContact+"\' , \'"+
                unnecessaryInfo.downstreamPointToPointTransmission+"\' , \'"+
                unnecessaryInfo.downstreamFileTransferMethod+"\' , \'"+
                unnecessaryInfo.developmentTeamDescription+"\' , \'"+
                unnecessaryInfo.applicant+"\' , \'"+
                unnecessaryInfo.applicationTime+"\' , \'"+
                unnecessaryInfo.executionScopeDescription+"\' , \'"+
                ");\r\n");
//        System.out.println("INSERT INTO table_name(interface_input_parameters)\r\n"+"VALUES("+necessaryInfo.interfaceInputParameters+");\r\n");
//        System.out.println("INSERT INTO table_name(is_retrys_supported)\r\n"+"VALUES("+necessaryInfo.isRetrySupported+");\r\n");
//        System.out.println("INSERT INTO table_name(is_interrupt_possible)\r\n"+"VALUES("+necessaryInfo.isInterruptPossible+");\r\n");
//        System.out.println("INSERT INTO table_name(estimated_timeIn_minutes)\r\n"+"VALUES("+necessaryInfo.estimatedTimeInMinutes+");\r\n");
//        System.out.println("INSERT INTO table_name(application_type)\r\n"+"VALUES("+necessaryInfo.applicationType+");\r\n");
//        System.out.println("INSERT INTO table_name(effective_date)\r\n"+"VALUES("+necessaryInfo.effectiveDate+");\r\n");
//        System.out.println("INSERT INTO table_name(delivery_date)\r\n"+"VALUES("+necessaryInfo.deliveryDate+");\r\n");
//        System.out.println("INSERT INTO table_name(production_date)\r\n"+"VALUES("+necessaryInfo.productionDate+");\r\n");
//        System.out.println("INSERT INTO table_name(version)\r\n"+"VALUES("+necessaryInfo.version+");\r\n");
//        System.out.println("INSERT INTO table_name(project_name)\r\n"+"VALUES("+necessaryInfo.projectName+");\r\n");
//        System.out.println("INSERT INTO table_name(requirement_sub_item)\r\n"+"VALUES("+necessaryInfo.requirementSubItem+");\r\n");
//        System.out.println("INSERT INTO table_name(application)\r\n"+"VALUES("+necessaryInfo.application+");\r\n");
//        System.out.println("INSERT INTO table_name(batch_category)\r\n"+"VALUES("+necessaryInfo.batchCategory+");\r\n");
//        System.out.println("INSERT INTO table_name(batch_session)\r\n"+"VALUES("+necessaryInfo.batchSession+");\r\n");
//        System.out.println("INSERT INTO table_name(job_description)\r\n"+"VALUES("+necessaryInfo.jobDescription+");\r\n");
//        System.out.println("INSERT INTO table_name(prerequisite_job)\r\n"+"VALUES("+necessaryInfo.prerequisiteJob+");\r\n");
//        System.out.println("INSERT INTO table_name(execution_frequency)\r\n"+"VALUES("+necessaryInfo.executionFrequency+");\r\n");
//        System.out.println("INSERT INTO table_name(execution_scope)\r\n"+"VALUES("+necessaryInfo.executionScope+");\r\n");

//        System.out.println("INSERT INTO table_name(stored_procedure_interface)\r\n"+"VALUES("+unnecessaryInfo.storedProcedureInterface+");\r\n");
//        System.out.println("INSERT INTO table_name(input_parameter_description)\r\n"+"VALUES("+unnecessaryInfo.inputParameterDescription+");\r\n");
//        System.out.println("INSERT INTO table_name(output_parameters)\r\n"+"VALUES("+unnecessaryInfo.outputParameters+");\r\n");
//        System.out.println("INSERT INTO table_name(interruption_solution)\r\n"+"VALUES("+unnecessaryInfo.interruptionSolution+");\r\n");
//        System.out.println("INSERT INTO table_name(upstream_application)\r\n"+"VALUES("+unnecessaryInfo.upstreamApplication+");\r\n");
//        System.out.println("INSERT INTO table_name(file_interface_name)\r\n"+"VALUES("+unnecessaryInfo.fileInterfaceName+");\r\n");
//        System.out.println("INSERT INTO table_name(is_file_structure_changed)\r\n"+"VALUES("+unnecessaryInfo.isFileStructureChanged+");\r\n");
//        System.out.println("INSERT INTO table_name(job_name)\r\n"+"VALUES("+unnecessaryInfo.jobName+");\r\n");
//        System.out.println("INSERT INTO table_name(execution_frequency_description)\r\n"+"VALUES("+unnecessaryInfo.executionFrequencyDescription+");\r\n");
//        System.out.println("INSERT INTO table_name(upstream_application_chinese_name)\r\n"+"VALUES("+unnecessaryInfo.upstreamApplicationChineseName+");\r\n");
//        System.out.println("INSERT INTO table_name(is_temporary_table_fields_required)\r\n"+"VALUES("+unnecessaryInfo.isTemporaryTableFieldsRequired+");\r\n");
//        System.out.println("INSERT INTO table_name(hasLegacy_files)\r\n"+"VALUES("+unnecessaryInfo.hasLegacyFiles+");\r\n");
//        System.out.println("INSERT INTO table_name(upstream_text_description)\r\n"+"VALUES("+unnecessaryInfo.upstreamTextDescription+");\r\n");
//        System.out.println("INSERT INTO table_name(upstream_contact)\r\n"+"VALUES("+unnecessaryInfo.upstreamContact+");\r\n");
//        System.out.println("INSERT INTO table_name(upstream_file_fransfer_method)\r\n"+"VALUES("+unnecessaryInfo.upstreamFileTransferMethod+");\r\n");
//        System.out.println("INSERT INTO table_name(upstream_point_to_point_transmission)\r\n"+"VALUES("+unnecessaryInfo.upstreamPointToPointTransmission+");\r\n");
//        System.out.println("INSERT INTO table_name(downstream_application)\r\n"+"VALUES("+unnecessaryInfo.downstreamApplication+");\r\n");
//        System.out.println("INSERT INTO table_name(downstream_target_interface)\r\n"+"VALUES("+unnecessaryInfo.downstreamTargetInterface+");\r\n");
//        System.out.println("INSERT INTO table_name(downstream_contact)\r\n"+"VALUES("+unnecessaryInfo.downstreamContact+");\r\n");
//        System.out.println("INSERT INTO table_name(downstream_point_to_point_transmission)\r\n"+"VALUES("+unnecessaryInfo.downstreamPointToPointTransmission+");\r\n");
//        System.out.println("INSERT INTO table_name(downstream_file_transfer_method)\r\n"+"VALUES("+unnecessaryInfo.downstreamFileTransferMethod+");\r\n");
//        System.out.println("INSERT INTO table_name(development_team_description)\r\n"+"VALUES("+unnecessaryInfo.developmentTeamDescription+");\r\n");
//        System.out.println("INSERT INTO table_name(applicant)\r\n"+"VALUES("+unnecessaryInfo.applicant+");\r\n");
//        System.out.println("INSERT INTO table_name(application_time)\r\n"+"VALUES("+unnecessaryInfo.applicationTime+");\r\n");
//        System.out.println("INSERT INTO table_name(execution_scope_description)\r\n"+"VALUES("+unnecessaryInfo.executionScopeDescription+");\r\n");

        return "123";
    }
}
