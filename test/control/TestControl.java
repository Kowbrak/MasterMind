package control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import boardifier.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestControl {

    public class NextPlayerTest {

        private Player player;

        @BeforeEach
        public void setUp() {
            player = mock(Player.class);

        }

    }
}
