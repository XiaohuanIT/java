import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
/**
 * @Author: xiaohuan
 * @Date: 2019-08-17 21:41
 */
public class Test {
  public static void main(String[] args) throws IOException {
    //ClassReader用于读取原有字节码，ClassWriter用于写入字节码，
    ClassWriter cs = new ClassWriter(0);
    //通过vist确定类的同步信息 java版本号 类修饰符 类的权限定名
    cs.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"Duck",null, "java/lang/Object",null);
    //构造函数
    MethodVisitor mv = cs.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
    mv.visitCode();
    mv.visitVarInsn(Opcodes.ALOAD, 0);
    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
    mv.visitInsn(Opcodes.RETURN);
    mv.visitMaxs(1, 1);
    mv.visitEnd();
    // 定义code方法
    MethodVisitor methodVisitor = cs.visitMethod(Opcodes.ACC_PUBLIC, "code", "()V", null, null);
    methodVisitor.visitCode();
    methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    methodVisitor.visitLdcInsn("I'm a Duck,Just Coding.....");
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
    methodVisitor.visitInsn(Opcodes.RETURN);
    methodVisitor.visitMaxs(2, 2);
    methodVisitor.visitEnd();
    cs.visitEnd();
    // 使classWriter类已经完成
    // 将classWriter转换成字节数组写到文件里面去
    byte[] data = cs.toByteArray();
    File file = new File("/Users/yangxiaohuan/Downloads/Duck.class");
    FileOutputStream fout = new FileOutputStream(file);
    fout.write(data);
    fout.close();
  }
}
