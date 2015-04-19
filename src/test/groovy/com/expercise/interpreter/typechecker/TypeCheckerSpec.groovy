package com.expercise.interpreter.typechecker

import com.expercise.enums.DataType
import spock.lang.Specification

class TypeCheckerSpec extends Specification {

    TypeChecker typeChecker

    def setup() {
        typeChecker = new TypeChecker()
        typeChecker.init()
    }

    def "should check type as valid if value is integer and specified data type is integer also"() {
        expect:
        typeChecker.typeCheck(49, DataType.Integer)
    }

    def "should check type as valid if value is text and specified data type is text also"() {
        expect:
        typeChecker.typeCheck("49", DataType.Text)
    }

    def "should check type as invalid if value type and specified type does not matched"() {
        expect:
        !typeChecker.typeCheck("49", DataType.Integer)
        !typeChecker.typeCheck(49, DataType.Text)
    }

}
