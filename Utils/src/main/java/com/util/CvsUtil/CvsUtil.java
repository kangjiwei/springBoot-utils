package com.util.CvsUtil;

import com.alibaba.fastjson.JSON;
import com.opencsv.CSVReader;
import com.util.Entity.Student;
import lombok.Cleanup;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

@Data
@Slf4j
public class CvsUtil {

    private String filePath;
    private MultipartFile file;
    private List<String> titles;
    private List<Map> dataList;
    private List<List<String>> allDatas;

    public CvsUtil(String filePath) throws Exception {
        this.filePath = filePath;
        @Cleanup FileInputStream fileInputStream = new FileInputStream(filePath);
        @Cleanup InputStreamReader reader = new InputStreamReader(fileInputStream);
        Optional<List<List<String>>> cvsDatas = this.parse(reader);
        if (cvsDatas.isPresent()) {
            allDatas = cvsDatas.get();
        }
        this.getCvsTitles();
        this.getDatas();
    }

    public CvsUtil(MultipartFile file) throws IOException {
        this.file = file;
        @Cleanup InputStream inputStream = file.getInputStream();
        @Cleanup InputStreamReader reader = new InputStreamReader(inputStream);
        Optional<List<List<String>>> cvsDatas = this.parse(reader);
        if (cvsDatas.isPresent()) {
            allDatas = cvsDatas.get();
        }
        this.getCvsTitles();
        this.getDatas();
    }

    /**
     * 解析文件
     *
     * @param reader
     * @return
     */
    private Optional<List<List<String>>> parse(InputStreamReader reader) {
        List<List<String>> retList = new ArrayList<>();
        try {
            @Cleanup CSVReader csvReader = null;
            csvReader = new CSVReader(reader);
            String[] strs;
            while ((strs = csvReader.readNext()) != null) {
                if (!StringUtils.isEmpty(strs) && strs.length >= 1) {
                    log.debug("读取数据:{}", strs);
                    strs = strs[0].split(";");
                    retList.add(Arrays.asList(strs));
                } else {
                    log.debug("数据是空的");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
        return Optional.of(retList);
    }

    /**
     * 获取标题
     * 默认数据第一行是标题
     *
     * @param
     */
    private void getCvsTitles() {
        if (allDatas.size() > 0) {
            titles = allDatas.get(0);
        } else {
            titles = new ArrayList<>();
        }
    }


    /**
     * 获取数据
     */
    private void getDatas() {
        dataList = new ArrayList<>();
        Map<String, Object> dataMap;
        List<String> datas;
        if (allDatas.size() > 0) {
            //删除标题
            allDatas.remove(0);
            for (int i = 0; i < allDatas.size(); i++) {
                datas = allDatas.get(i);
                dataMap = new HashMap<>();
                for (int t = 0; t < titles.size(); t++) {
                    log.debug(datas.get(t));
                    dataMap.put(titles.get(t), datas.get(t));
                }
                log.debug("解析CSV文件--{}行数据:[{}]", i, dataMap);
                dataList.add(dataMap);
            }
        }
    }

    public static void main(String[] args) {
        String path = "D:\\工作文件\\cvs\\index.csv";
        File file = new File(path);
        try {
            MultipartFile mulFile = new MockMultipartFile(
                    "index.csv", //文件名
                    "haha.jpg", //originalName 相当于上传文件在客户机上的文件名
                    ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                    new FileInputStream(path) //文件流
            );
            CvsUtil cvsUtil = new CvsUtil(mulFile);
            List<String> titles = cvsUtil.getTitles();
            List<Map> dataList = cvsUtil.getDataList();
            log.info("titles:[{}]", JSON.toJSONString(titles));
            log.info("datas:[{}]", JSON.toJSONString(dataList));

            CsvPageUtil pageUtil = new CsvPageUtil<Map>(dataList);
            Integer total = pageUtil.getTotalNum();
            log.debug("总数:[{}]", total);
            List<Map> limit = pageUtil.limit(6, 10);
            for (Map map : limit) {
                log.debug(JSON.toJSONString(map));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        /**
//         * 如果数据总数小于pageSize
//         * 只有一页
//         */
//        Integer num = 2 / 10;
//        /**
//         * 整除是0，
//         * 不整除是余数
//         */
//        Integer num1 = 8 % 10;
//
//        log.debug("除以：{},{},{}", num, num1, (1-1) * 10);

        Student student = new Student("sss","男");



    }

}
