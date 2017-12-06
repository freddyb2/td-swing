package com.fredericboisguerin.insa.calculateurprix.ui;

import javax.swing.*;

public class SpringPanel extends JPanel {

    SpringPanel() {
        super(new SpringLayout());
    }

    public void makeCompactGrid(int numberOfRows, int numberOfColumns, int margin, int padding) {
        Spring x = alignCellsVertically(numberOfRows, numberOfColumns, margin, padding);
        Spring y = alignCellsHorizontally(numberOfRows, numberOfColumns, margin, padding);
        SpringLayout.Constraints constraints = getLayout().getConstraints(this);
        constraints.setConstraint(SpringLayout.SOUTH, y);
        constraints.setConstraint(SpringLayout.EAST, x);
    }

    private Spring alignCellsHorizontally(int numberOfRows, int numberOfColumns, int margin, int padding) {
        Spring y = Spring.constant(margin);
        for (int r = 0; r < numberOfRows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < numberOfColumns; c++) {
                height = Spring.max(height, getConstraintsForCell(r, c, numberOfColumns).getHeight());
            }
            for (int c = 0; c < numberOfColumns; c++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r, c, numberOfColumns);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(padding)));
        }
        return y;
    }

    private Spring alignCellsVertically(int numberOfRows, int numberOfColumns, int margin, int padding) {
        Spring x = Spring.constant(margin);
        for (int columnId = 0; columnId < numberOfColumns; columnId++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < numberOfRows; r++) {
                width = Spring.max(width, getConstraintsForCell(r, columnId, numberOfColumns).getWidth());
            }
            for (int r = 0; r < numberOfRows; r++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r, columnId, numberOfColumns);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(padding)));
        }
        return x;
    }

    @Override
    public SpringLayout getLayout() {
        return (SpringLayout) super.getLayout();
    }

    private SpringLayout.Constraints getConstraintsForCell(int row, int col, int cols) {
        return getLayout().getConstraints(getComponent(row * cols + col));
    }
}
