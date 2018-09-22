package itsATestPackage.hello;

import itsAJavaSourcePackage.HelloWorld;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldTest {
    private HelloWorld testObj;

    @BeforeEach void beforeEach() {
        testObj = new HelloWorld();
    }

    @Nested class WhenPrintingToConsole {
        StringBuilder sb;

        @BeforeEach void beforeEach() {
            sb = new StringBuilder();

            OutputStream localStream = new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    sb.append(new String(new byte[]{(byte)b}));
                }
            };
            TeeOutputStream combinedLocalAndStdOut = new TeeOutputStream(System.out, localStream);
            PrintStream writeToBoth = new PrintStream(combinedLocalAndStdOut, true);

            System.setOut(writeToBoth);
        }

        @Test void helloWorldTest() {
            testObj.printIt();
            assertThat(sb.toString()).isEqualTo("Hello world!\n");
        }
    }
}
