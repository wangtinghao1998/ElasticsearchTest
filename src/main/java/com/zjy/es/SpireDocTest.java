package com.zjy.es;
import com.spire.doc.*;
import com.spire.doc.documents.CommentMark;
import com.spire.doc.documents.CommentMarkType;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TextSelection;
import com.spire.doc.fields.Comment;

import java.io.File;

public class SpireDocTest {

    public static void main(String[] args) {
        //加载测试文档
        Document doc = new Document();

        doc.loadFromFile("D:\\Filesave\\1.docx");


        //查找指定字符串
        TextSelection[] selections = doc.findAllString("加强队伍建设", true, false);

        //获取关键字符串所在段落
        Paragraph para = selections[0].getAsOneRange().getOwnerParagraph();
        int index = para.getChildObjects().indexOf(selections[0].getAsOneRange());

        //设置批注ID
        CommentMark start = new CommentMark(doc,1,CommentMarkType.Comment_Start);
//        start.setCommentId(1);
//        start.setType(CommentMarkType.Comment_Start);
        CommentMark end = new CommentMark(doc,1,CommentMarkType.Comment_End);
//        end.setType(CommentMarkType.Comment_End);
//        end.setCommentId(1);

        //添加批注内容
        String str = "给指定字符串添加批注";
        Comment comment = new Comment(doc);
        comment.getFormat().setCommentId(1);
        comment.getBody().addParagraph().appendText(str);
        comment.getFormat().setAuthor("作者：");
        comment.getFormat().setInitial("CM");
        para.getChildObjects().insert(index, start);
        para.getChildObjects().insert(index + 1, selections[0].getAsOneRange());
        para.getChildObjects().insert(index + 2,end);
        para.getChildObjects().insert(index + 3, comment);

        //保存文档
        doc.saveToFile("D:\\Filesave\\字符串批注.docx",FileFormat.Docx_2013);
        doc.dispose();
    }
}
