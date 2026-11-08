// The four capability statuses, in order from least to most real.
// Single definition shared by the validator, the site build and the updater.

export const STATUSES = ['fiction', 'research', 'prototype', 'shipping'];

export const LABELS = {
  fiction: 'Fiction',
  research: 'Research',
  prototype: 'Prototype',
  shipping: 'Shipping',
};

export const DEFINITIONS = {
  fiction: 'Nothing like it works outside stories.',
  research: 'Theory or lab results exist, but no working system does the job.',
  prototype:
    'A working version exists, but a consumer cannot buy it. This includes lab, military, professional-medical and spaceflight hardware.',
  shipping: 'A consumer can buy it today.',
};

export const rank = (status) => STATUSES.indexOf(status);
