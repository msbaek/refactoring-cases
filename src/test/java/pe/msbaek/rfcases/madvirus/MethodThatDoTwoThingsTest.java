package pe.msbaek.rfcases.madvirus;

import java.time.LocalDate;

import static pe.msbaek.rfcases.madvirus.MethodThatDoTwoThingsTest.ResultType.EXPECTED;
import static pe.msbaek.rfcases.madvirus.MethodThatDoTwoThingsTest.ResultType.SCHEDULED;

public class MethodThatDoTwoThingsTest {
    class ResultBuilder {
        public ResultBuilder period(InputPeriod expectedPeriod) {
            throw new UnsupportedOperationException("ResultBuilder::period not implemented yet");
        }

        public ResultBuilder type(ResultType expected) {
            throw new UnsupportedOperationException("ResultBuilder::type not implemented yet");
        }
    }

    class Mapper {
        public InputPeriod selectPeriod(Object param) {
            throw new UnsupportedOperationException("Mapper::selectPeriod not implemented yet");
        }

        public InputPeriod selectExpectedPeriod(Object otherParam) {
            throw new UnsupportedOperationException("Mapper::selectExpectedPeriod not implemented yet");
        }
    }

    class InputPeriod {
        public LocalDate getStart() {
            throw new UnsupportedOperationException("InputPeriod::getStart not implemented yet");
        }

        public LocalDate getEnd() {
            throw new UnsupportedOperationException("InputPeriod::getEnd not implemented yet");
        }
    }

    enum ResultType {
        EXPECTED, SCHEDULED
    }
    Mapper mapper;

    public ResultBuilder problematicMethod(Object param, Object otherParam, LocalDate today) {
        ResultBuilder builder = new ResultBuilder();
        
        InputPeriod realPeriod = mapper.selectPeriod(param);
        if(realPeriod == null) {
            InputPeriod expectedPeriod = mapper.selectExpectedPeriod(otherParam);
            builder.period(expectedPeriod).type(EXPECTED);
        }
        else if(realPeriod.getStart() == null || realPeriod.getEnd() == null) {
            builder.period(null).type(EXPECTED);
        }
        else {
            if((today.isEqual(realPeriod.getStart()) || today.isEqual(realPeriod.getEnd())) ||
                    (today.isAfter(realPeriod.getStart()) && today.isBefore(realPeriod.getEnd()))) {
                builder.period(realPeriod).type(SCHEDULED);
            }
            else {
                builder.period(realPeriod).type(EXPECTED);
            }
        }
        return builder;
    }
}