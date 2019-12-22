package xyz.ruankun.laughingspork.util;
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      Buddha Bless, No Bug !

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * �����֤��ĳ���
 * ȫ��ֻ��һ�� ������֤��浽����
 *
 */
public abstract class VerifyCodePool {

    private static final Logger logger = LoggerFactory.getLogger(VerifyCodePool.class);

    private VerifyCodePool(){}

    //��֤���
    //��ô�棿 <��֤��,����ʱ��>
    private static Map<String,Date> verifycodes = new ConcurrentHashMap<>(); //����

    //��֤ô�����Чʱ��  30 ����
    private final static long MAX_VALID_TIME = 1800l * 1000;

    //���������ֵ,�ﵽ���ֵ������һ��map�е�ֵ��
    private static final long MAX_COUNT = 10000l;

    //������ ÿ��
    private static Integer count = 0;

    /**
     * ����һ����֤��
     * @param code
     */
    public static void setVerifyCode(String code){
        count++;
        logger.info("set verifycode to pool:" + code);
        verifycodes.put(code, new Date());
        logger.info("��ȡ������֤�룺 get verifycode: " + verifycodes.get(code));
    }

    /**
     * ��֤��ɾ��һ����֤��
     * @param code
     * @return
     */
    public static boolean verify(String code){
        if(code == null) {
            return false;
        }
        code = code.toLowerCase();
        cleanPool();
        Date date = verifycodes.get(code);
        if(null == date){
            //��֤�벻����
            logger.info("verify code is not here");
            return false;
        }else {
            logger.info("��֤����֤:" + date.toString() + "�����code:" + code);
            //��֤���Ǵ��ڵ�
            //��֤���Ƿ����
            boolean outOfDate = new Date().getTime() > date.getTime() + MAX_VALID_TIME;
            //�Ѿ���֤���˾�Ҫ����֤��ɾ����
            verifycodes.remove(code);
            if (outOfDate){
                return false;
            }else {
                return true;
            }
        }
    }


    /**
     * һ�������һ�γ���
     */
    private static void cleanPool(){
        if (count >= MAX_COUNT){
            count = 0;
            // ������֤��ʱ��Ҫ�������ӣ�����ʹ�õ�ʱ������
            synchronized (VerifyCodePool.class){
                Map newMap = new ConcurrentHashMap<>();
                Set<String> set = verifycodes.keySet();
                for (String key :
                        set) {
                    Date date = verifycodes.get(key);
                    if (date.getTime() + MAX_VALID_TIME > new Date().getTime()){
                        // ֻ������Ч��
                        newMap.put(key, date);
                    }
                }
                //�ϵĳ���������?
                //�µĳ���
                verifycodes = newMap;
            }
        }

    }
}
