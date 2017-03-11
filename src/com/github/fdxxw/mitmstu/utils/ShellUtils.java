
/**   
 * @Title: ShellUtils.java 
 * @Package: com.github.fdxxw.utils 
 * @Description: TODO
 * @author fdxxw ucmxxw@163.com 
 * @date 2017年3月11日 下午3:27:44 
 */


package com.github.fdxxw.mitmstu.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

/** 
 * @Description shell工具
 * @author fdxxw ucmxxw@163.com
 * @date 2017年3月11日 下午3:27:44 
 */

public class ShellUtils {
    
    
    /** @Fields COMMAND_SU: 获取root权限命令*/
      	
    public final static String COMMAND_SU = "su" ;
    
    
    /** @Fields COMMAND_SH: shell解析器 /bin/sh  */
      	
    public final static String COMMAND_SH = "sh" ;
    
    
    /** @Fields COMMAND_EXIT: 退出终端 */
      	
    public final static String COMMAND_EXIT = "exit\n" ;
    
    
    /** @Fields COMMAND_LINE_END: 结束符*/
      	
    public final static String COMMAND_LINE_END = "\n" ;
    
     
    /** 
     * @Description 检查是否具有root权限
     * @author fdxxw ucmxxw@163.com
     * @return  
     */
      	
    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false, true).result == 0;
    }
    
     
    /** 
     * @Description 执行一个命令
     * @author fdxxw ucmxxw@163.com
     * @param command
     * @param isRoot
     * @param isNeedResultMsg
     * @param exit
     * @return  
     */
      	
    public static CommandResult execCommand(String command, boolean isRoot,
            boolean isNeedResultMsg, boolean exit) {
        return execCommand(new String[] { command }, isRoot, isNeedResultMsg,
                exit);
    }

    
    public static CommandResult execCommand(String[] commands,boolean isNeedRoot,boolean isNeedResultMsg,boolean exit ) {
        int result = -1;
        if(commands == null || commands.length == 0) {
            return new CommandResult(result,null,null);
        }
        
        Process process = null;
        
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        
        DataOutputStream os = null;
        
        try {
            process = Runtime.getRuntime().exec(isNeedRoot ? COMMAND_SU:COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for(String command : commands) {
                if(command == null) {
                    continue;
                }
                os.write(command.getBytes());
                os.write(COMMAND_LINE_END.getBytes());
                os.flush();
            }
            if(exit) {
                os.write(COMMAND_EXIT.getBytes());
                os.flush();
            }
            result = process.waitFor();
            if(isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                String s;
                while((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
            
        }catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            try {
                if(os != null) {
                    os.close();
                }
                if(successResult != null) {
                    successResult.close();
                }
                if(errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {
                // TODO: handle exception\
                e.printStackTrace();
            }
            if(process != null) {
                process.destroy();
            }
        }
        
        return new CommandResult(result, successMsg == null ? null
                : successMsg.toString(), errorMsg == null ? null
                : errorMsg.toString());
    }
    
    
    /** 
     * @Description 执行命令返回的结果
     * @author fdxxw ucmxxw@163.com
     * @date 2017年3月11日 下午4:23:13 
     */ 
      	
    public static class CommandResult {
        
        
        /** @Fields result:命令执行结果 */
          	
        public int result ;
        
        /** @Fields successMsg: 命令执行成功的消息*/
          	
        public String successMsg;
        
        /** @Fields errorMsg:命令执行失败的消息 */
          	
        public String errorMsg; 
        
        public CommandResult() {}
        
        public CommandResult(int result) {
            this.result = result;
        }
        
        public CommandResult(int result,String successMsg,String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
