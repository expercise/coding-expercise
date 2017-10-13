package com.expercise.interpreter.typechecker

import com.expercise.enums.DataType
import com.expercise.interpreter.typechecker.javascript.JavaScriptTypeChecker
import spock.lang.Specification

class JavaScriptTypeCheckerSpec extends Specification {

    JavaScriptTypeChecker typeChecker

    def setup() {
        typeChecker = new JavaScriptTypeChecker()
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

    def "should check type as valid if value is array and specified data type is array also"() {
        expect:
        typeChecker.typeCheck([1, 2, 3], DataType.Array)
    }

    def "should check type as invalid if value type and specified type does not matched"() {
        expect:
        !typeChecker.typeCheck("49", DataType.Integer)
        !typeChecker.typeCheck(49, DataType.Text)
        !typeChecker.typeCheck([1, 2, 3, 4], DataType.Text)
    }

}
