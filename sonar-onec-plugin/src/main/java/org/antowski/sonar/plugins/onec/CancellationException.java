
package org.antowski.sonar.plugins.onec;

/**
 * Exception thrown when the context is cancelled.
 *
 */
class CancellationException extends RuntimeException {

  private static final long serialVersionUID = 2694991398328066200L;

  CancellationException(String message) {
    super(message);
  }

}
