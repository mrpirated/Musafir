#include <stdio.h>
#include <stdlib.h>
struct stack
{
    int top;
    int *stck;
    int size;
};
struct stack *initialize(struct stack *s, int n)
{
    s->top = -1;
    s->size = n;
    s->stck = (int *)malloc(n * sizeof(int));
    return s;
}
void push(struct stack **s, int n)
{
    if ((*s)->top == (*s)->size - 1)
    {
        printf("Oveflow");
        return;
    }
    (*s)->stck[++(*s)->top] = n;
    return;
}
int isempty(struct stack *s)
{
    if (s->top == -1)
        return 1;

    else
        return 0;
}
int top(struct stack *s)
{
    if (isempty(s))
    {
        printf("Stack is empty\n");
        return -1;
    }
    return s->stck[s->top];
}
void pop(struct stack **s)
{
    if (isempty(*s))
    {
        printf("Undeflow\n");
        return;
    }
    (*s)->top--;
    return;
}

int main()
{
    struct stack *st1 = (struct stack *)malloc(sizeof(struct stack));
    int n, x, k = 0;
    printf("Enter the size of stack: ");
    scanf("%d", &n);
    printf("\n1. Push the element in stack\n");
    printf("2. Pop the element in stack\n");
    printf("3. Display the elements in stack\n");
    printf("Enter 4 for exit\n");

    st1 = initialize(st1, n);
    while (k != 4)
    {
        printf("\nEnter the choice : ");

        scanf("%d", &k);

        switch (k)
        {
        case 1:
            printf("Enter the element to push in the stack: ");
            scanf("%d", &x);
            push(&st1, x);
            break;

        case 2:
            pop(&st1);
            break;

        case 3:
            while (!isempty(st1))
            {
                printf("%d\n", top(st1));
                pop(&st1);
            }
            break;
        }
    }
}