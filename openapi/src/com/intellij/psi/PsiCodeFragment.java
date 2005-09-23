/*
 * Copyright 2000-2005 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.psi;

import org.jetbrains.annotations.NonNls;

/**
 * Represents a fragment of Java code which exists outside of a project structure (for example,
 * in a foreign language code or in a user interface element other than the main source code editor).
 */
public interface PsiCodeFragment extends PsiFile, PsiImportHolder {
  /**
   * @deprecated use #setVisibilityChecker(VisibilityChecker.EVERYTHING_VISIBLE) instead.
   */
  void setEverythingAcessible(boolean value);

  /**
   * Returns the type corresponding to the <code>this</code> keyword in the code fragment.
   *
   * @return the type of <code>this</code> in the fragment.
   */
  PsiType getThisType();

  /**
   * Sets the type corresponding to the <code>this</code> keyword in the code fragment.
   *
   * @param psiType the type of <code>this</code> in the fragment.
   */
  void setThisType(PsiType psiType);

  /**
   * Returns the type corresponding to the <code>super</code> keyword in the code fragment.
   *
   * @return the type of <code>super</code> in the fragment.
   */
  PsiType getSuperType();

  /**
   * Sets the type corresponding to the <code>super</code> keyword in the code fragment.
   *
   * @param superType the type of <code>super</code> in the fragment.
   */
  void setSuperType(PsiType superType);

  /**
   * Returns the list of classes considered to be imported by the code fragment.
   *
   * @return the comma-separated list of full-qualified names of classes considered
   * to be imported.
   */
  String importsToString();

  /**
   * Adds the specified classes to the list of classes considered to be imported by the
   * code fragment.
   *
   * @param imports the comma-separated list of full-qualified names of classes to import.
   */
  void addImportsFromString(String imports);

  /**
   * Sets the visibility checker which is used to determine the visibility of declarations
   * from the code fragment.
   *
   * @param checker the checker instance.
   */
  void setVisibilityChecker(VisibilityChecker checker);

  /**
   * Gets the visibility checker which is used to determine the visibility of declarations
   * from the code fragment.
   *
   * @return the checker instance.
   */
  VisibilityChecker getVisibilityChecker();

  /**
   * Sets the exception handler which is used to determine which exceptions are considered handled
   * in the context where the fragment is used.
   *
   * @param checker the exception handler instance.
   */
  void setExceptionHandler(ExceptionHandler checker);

  /**
   * Gets the exception handler which is used to determine which exceptions are considered handled
   * in the context where the fragment is used.
   *
   * @return the exception handler instance.
   */
  ExceptionHandler getExceptionHandler();

  /**
   * @fabrique override it as appropriate to you
   */
  boolean isPhysicalChangesProvider();

  /**
   * Interface used to determine the visibility of declarations from the code fragment.
   */
  interface VisibilityChecker {
    /**
     * Returns the visibility of the specified declaration from the specified location.
     *
     * @param declaration the referenced declaration.
     * @param place the location of the reference to the declaration.
     * @return the visibility of the declaraion.
     */
    Visibility isDeclarationVisible(PsiElement declaration, PsiElement place);

    public class Visibility {
      /**
       * The declaration is visible from the location.
       */
      public static final Visibility VISIBLE = new Visibility("VISIBLE");

      /**
       * The declaration is not visible from the location.
       */
      public static final Visibility NOT_VISIBLE = new Visibility("NOT_VISIBLE");

      /**
       * The visibility of the declaration from the location is defined by Java scoping rules.
       */
      public static final Visibility DEFAULT_VISIBILITY = new Visibility("DEFAULT_VISIBILITY");

      private final String myName; // for debug only

      private Visibility(@NonNls String name) {
        myName = name;
      }

      public String toString() {
        return myName;
      }
    }

    /**
     * The visibility checker which reports all declarations as visible.
     *
     * @since 5.0.2
     */
    VisibilityChecker EVERYTHING_VISIBLE = new VisibilityChecker() {
      public Visibility isDeclarationVisible(PsiElement declaration, PsiElement place) {
        return Visibility.VISIBLE;
      }
    };
  }

  /**
   * Interface used to determine which exceptions are considered handled
   * in the context where the fragment is used.
   */
  interface ExceptionHandler {
    /**
     * Checks if the specified exception is considered handled
     * in the context where the fragment is used.
     *
     * @param exceptionType the type of the exception to check.
     * @return true if the exception is handled, false otherwise.
     */
    boolean isHandledException(PsiClassType exceptionType);
  }
}
