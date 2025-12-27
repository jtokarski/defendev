package org.defendev.common.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;



public class PathUtilTest {

    @Test
    public void outputFilePathValidSuccess(@TempDir final Path tempDir) {
        // given
        final Path filePath = tempDir.resolve("test.sql");

        // when
        final Path verifiedPath = PathUtil.outputFilePath(filePath.toString());

        // then
        assertThat(verifiedPath).isEqualTo(filePath);
    }

    @Test
    public void outputFilePathInvalidFail(@TempDir final Path tempDir) {
        // given
        final Path filePath = tempDir.resolve("nonexistent/test.sql");

        // when
        final Throwable thrown = catchThrowable(() -> PathUtil.outputFilePath(filePath.toString()));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void outputFilePathInvalidCreateParent(@TempDir final Path tempDir) {
        // given
        final Path filePath = tempDir.resolve("non/existent/nested/test.sql");

        // when
        final Path verifiedPath = PathUtil.outputFilePath(filePath.toString(), true);

        // then
        assertThat(verifiedPath).isEqualTo(filePath);
        assertThat(filePath.getParent()).exists();
    }

}
