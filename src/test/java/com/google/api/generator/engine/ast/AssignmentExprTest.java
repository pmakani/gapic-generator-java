// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.engine.ast;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class AssignmentExprTest {
  @Test
  public void assignMatchingValue() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr valueExpr = ValueExpr.builder().setValue(value).build();

    assertValidAssignmentExpr(variableExpr, valueExpr);
  }

  @Test
  public void assignMismatchedValue() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable =
        Variable.builder().setIdentifier(identifier).setType(TypeNode.BOOLEAN).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr valueExpr = ValueExpr.builder().setValue(value).build();

    assertInvalidAssignmentExpr(variableExpr, valueExpr);
  }

  @Test
  public void assignMatchingVariable() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    IdentifierNode anotherIdentifier = IdentifierNode.builder().setName("y").build();
    Variable anotherVariable =
        Variable.builder().setIdentifier(anotherIdentifier).setType(TypeNode.INT).build();
    Expr valueExpr = VariableExpr.builder().setVariable(anotherVariable).build();

    assertValidAssignmentExpr(variableExpr, valueExpr);
  }

  private static void assertInvalidAssignmentExpr(VariableExpr variableExpr, Expr valueExpr) {
    assertThrows(
        TypeMismatchException.class,
        () -> {
          AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
        });
  }

  private static void assertValidAssignmentExpr(VariableExpr variableExpr, Expr valueExpr) {
    AssignmentExpr assignMentExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
    // No exception, we succeeded.
    assertThat(assignMentExpr.variableExpr()).isEqualTo(variableExpr);
    assertThat(assignMentExpr.valueExpr()).isEqualTo(valueExpr);
  }
}
