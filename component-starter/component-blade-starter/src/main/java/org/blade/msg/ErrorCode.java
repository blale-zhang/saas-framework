package org.blade.msg;

/**
 * 错误代码
 */
public class ErrorCode {

    /**
     * 传递参数错误
     */
    public static class Params{

        public static int  Wrong_number_of_parameters             =       30000; // 参数个数有误
        public static int  Wrong_number_of_required_parameters    =   30001; // 必填参数个数有误
        public static int  Wrong_parameter_type                   =      30002; // 参数类型错误
        public static int  Wrong_number_of_parameter_values       =       30003; // 参数值数有误
        public static int  Numeric_parameters_are_out_of_range    =    30101; // 数值参数超出范围
        public static int  Wrong_date_parameter                   =        30201; // 日期参数有误
        public static int  Date_parameter_format_error            =   30202; // 日期参数格式用误
    }

    /**
     * 调用成功
     */
    public static class Success{
        public static int  SUCCESS                                =  1; // 正确执行并返回
    }

    /**
     * 业务参数
     */
    public static class Business {
        public static int  Invalid_organId                        =     40000; // 无效的 organId
        public static int  Invalid_userId                         =     40001; // 无效的 userId
        public static int  Invalid_orderId                        =     40002; // 无效的 orderId
    }

}




