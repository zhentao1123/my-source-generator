package org.catcafe.generator.jpa;

import static org.apache.commons.logging.LogFactory.getLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * FreeMarker工具类
 * 
 * FreeMarkerUtils
 * 
 * 官方不建议无谓的创建Configuration，即推荐全局单例的Configuration，该处由调用方保证。
 * 
 */
public class FreeMarkerUtils{
    private final static Log log = getLog(FreeMarkerUtils.class);
        
    /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFileByFile(String templateFileName, String destFilePath, Configuration configuration,
            Object model) throws IOException, TemplateException {
        generateFileByFile(templateFileName, destFilePath, configuration, model, true, false);
    }

    /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @param override
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFileByFile(String templateFileName, String destFilePath, Configuration configuration,
            Object model, boolean override) throws IOException, TemplateException {
        generateFileByFile(templateFileName, destFilePath, configuration, model, override, false);
    }

    /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @param override
     * @param append
     * @throws ParseException
     * @throws MalformedTemplateNameException
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFileByFile(String templateFileName, String destFilePath, Configuration configuration,
            Object model, boolean override, boolean append)
            throws MalformedTemplateNameException, ParseException, IOException, TemplateException 
    {
    		Template t = configuration.getTemplate(templateFileName);
        File destFile = new File(destFilePath);
        if (override || append || !destFile.exists()) {
            File parent = destFile.getParentFile();
            if (null != parent) {
                parent.mkdirs();
            }
            try (FileOutputStream outputStream = new FileOutputStream(destFile, append);
                    Writer out = new OutputStreamWriter(outputStream, DEFAULT_CHARSET);) {
                t.process(model, out);
            }
            destFile.setReadable(true, false);
            destFile.setWritable(true, false);
            log.info(destFilePath + "    saved!");
        } else {
            log.error(destFilePath + "    already exists!");
        }
    }

    /**
     * @param template
     * @param configuration
     * @return
     * @throws TemplateException
     * @throws IOException
     */
    public static String generateStringByFile(String template, Configuration configuration)
            throws IOException, TemplateException {
        return generateStringByFile(template, configuration, new HashMap<String, Object>());
    }

    /**
     * @param template
     * @param configuration
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String generateStringByFile(String template, Configuration configuration, Map<String, Object> model)
            throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        generateStringByFile(writer, template, configuration, model);
        return writer.toString();
    }

    /**
     * @param writer
     * @param template
     * @param configuration
     * @param model
     * @throws TemplateNotFoundException
     * @throws MalformedTemplateNameException
     * @throws ParseException
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateStringByFile(Writer writer, String template, Configuration configuration,
            Map<String, Object> model)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Template tpl = configuration.getTemplate(template);
        tpl.process(model, writer);
    }

    /**
     * @param templateContent
     * @param configuration
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String generateStringByString(String templateContent, Configuration configuration, Map<String, Object> model)
            throws IOException, TemplateException {
        Template tpl = new Template(String.valueOf(templateContent.hashCode()), templateContent, configuration);
        StringWriter writer = new StringWriter();
        tpl.process(model, writer);
        return writer.toString();
    }
    
    /**
     * 默认字符编码名称
     * 
     * Default CharSet Name
     */
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    /**
     * 默认字符编码
     * 
     * Default CharSet
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);
}
